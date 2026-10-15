# DRY preservation regression suite

The oracle contains synthetic data only and is deliberately outside the checkout.
Do not regenerate it after changing production sources or templates.

## Run the identical preservation tests before and after the refactor

From the repository root in PowerShell:

```powershell
$env:JAVA_HOME = 'C:\devtools\jdk21'
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
$baseline = 'C:\Users\D00I657\.copilot\session-state\52cc97b7-7c5b-44ae-a4ba-c92c58a4455b\files\dry-baseline'
mvn --no-transfer-progress test "-Ddry.baseline=$baseline" "-Ddry.reportsDirectory=$baseline\reports-after" '-Duser.timezone=Europe/Rome'
```

Use `reports-before-compare` instead of `reports-after` for the pre-refactor
comparison run. Report destinations do not affect cases or expected outcomes.
Missing baselines and removed original templates fail rather than silently
creating expectations. Added macro libraries receive additional parse checks;
the original 66 templates still run the same 396 frozen rendering cases.
The initial recording used `-Ddry.record=true`; a `SEALED`
marker now rejects that option. Existing oracle files are never overwritten.

## Coverage: 531 passing cases

* 62 domain cases: three existing all-argument/no-argument constructors, exact
  JavaBean property inventory and accessor round trips (including capitalized
  `NTessera`), equality/hash consistency, equality sensitivity to each of the
  56 existing fields, and cross-type inequality.
* Six JDBC row cases: Ragazzo, TerzaMedia and Animatore with populated and
  nullable optional columns / absent left-joined squad. Frozen semantic object
  snapshots check values and all nested registration/reference objects.
* 66 template parse cases and 396 frozen renders: every WEB-INF FTL, including
  all six participant signup/edit forms, all six participant detail/print
  sheets, assignments, payments, lists, dashboards, statistics, other reports,
  contacts, accounts, errors, JSON responses, and shared fragments/macros.
* Every template runs six scenarios: full-day populated/admin; morning,
  unchecked options/secretary; afternoon/user; absent optional values and
  unassigned squad; empty collections; errors and closed registrations.
  Page-specific fixtures preserve real sequence/hash shapes and paid/unpaid,
  selected/unselected, reserved/unreserved and annotation ownership branches.
* One normalizer sensitivity case proves that text, field names, values,
  required flags, JavaScript parameters, and textarea/pre-wrap whitespace
  changes remain observable.

Normalization sorts attributes, ignores HTML comments and indentation-only
whitespace, and removes line-wrap indentation from ordinary text. It retains
attribute values (including IDs, names, required/Parsley/placeholder attributes,
selected/checked state), internal text spaces, table structure, script source,
and exact textarea/preformatted text. It does not use a forgiving HTML parser
that could repair legacy markup and hide changes. Configuration uses
FreeMarker 2.3.35, UTF-8, Italian locale and Europe/Rome time zone; JDBC-shaped
date fixtures use `java.sql.Date`, as the real row mappers do.

## Deliberate exclusions

These are preservation tests, not optional-clear correction tests. No test
asserts that submitting an empty optional controller parameter currently clears
persistence. The relevant public controllers immediately use static final DAO
instances through `DAOMan`, whose static initializer opens a configured Hikari
database pool. There is no injectable connection/DAO seam. Therefore controller
submission tests, JDBC bind/integration tests, real database persistence,
mail delivery, browser JavaScript execution and external network calls are
excluded. No live data or credentials are loaded.

The intentional optional-clear fix must be verified separately, without
changing these oracles or accepting differences in this preservation suite.
All 66 FTLs parse/render; macro-only fragments also render indirectly through
their calling pages. Synthetic coverage is broad but not exhaustive across
every possible branch combination.

## Final additional checks

The complete post-refactor suite initially had 640 passing cases, including the unchanged
531 preservation cases above. The remaining cases cover inherited properties,
role policy, request mapping and optional clearing, JDBC bind contracts,
controller helpers, Excel cells, and 13 rendered pages with cleared profile
and contact values. These new helper/correction cases are not represented as
pre-refactor preservation tests.

The Excel assertions were also run against the original implementation and
the refactored implementation with the same results. Optional-clearing checks
were compared separately: original mappings failed 27 of 60 correction cases,
whereas the new mappings passed all 60. This is the requested behavior change,
not an accepted difference in the frozen rendering oracle.

The final WAR build uses the same command with `package` in place of `test`.
The user-facing database/browser checklist and complete modified-template list
are in `TEST-MANUALI.md` at the repository root.

## FreeMarker simplification

Generic HTML macros have been removed. Layout, table headers, basic inputs,
checkboxes and page scripts are explicit in the pages again. Only application
sections are shared: participant data, registration/edit sections, account
data, emergency contacts and authorized companions. The Java refactor is
unchanged.

At this simplification stage, the same 396 frozen renders verified all original
templates without updating any oracle. The new `struct/iscrizione.html.ftl` receives a parse check
and is rendered through the six participant forms and both account forms.
Cleared-value rendering now covers 22 pages, including all six participant
forms and the four reports displaying the account's optional phone.
With the added library and null cases, the suite contained 650 tests.
Splitting the display macros into `dettaglioIscritto.html.ftl`,
`riferimentiIscritto.html.ftl` and `avvisiValidazione.html.ftl` adds three
parse cases (653 total). Pages import these libraries directly;
`macros.html.ftl` only includes them for compatibility with existing callers
and the frozen original-template inventory.

## User-approved form unification

The frozen files remain sealed and unchanged. `ApprovedFormChanges` applies
only the enumerated user decisions to the **expected** output of the six
participant forms, account edit form and secretary registration form.
All 396 cases still compare the complete rendered output: 348 use the original
expectation verbatim, and 48 allow exactly the approved labels, field order,
layout, popup height, year placement and required account fields.
Unrelated attributes, values, selections, scripts and content remain checked.

The four account columns (telefono, localita, via, civico) are NOT NULL in
`GrestDb/flyway/V7__Registrato.sql`. Their previous 16 optional-clearing cases
are superseded by 12 missing/empty/blank rejection cases and one valid mapping
case. These are an intentional requirement correction, not preservation
failures. Secretary registration now includes the same required account fields.
The current full suite contains 650 tests.

## DAO mapper extraction

`DAOManager.Mappers.IscrittoDAOMapper` now owns the complete Ragazzo,
TerzaMedia and Animatore row mappings, not just their common fields.
`PagamentoDAOMapper` owns both payment variants and their registration-prefix
overloads; `ExportDAOMapper` owns the three complete export records.
`RegistratoDAOMapper` handles ordinary account mappings for accounts, contacts,
annotations, participants and payments. Export account normalization remains
separate in `ExportDAOMapper`.

`MapperExtractionTest` compares every component of populated and nullable export
records and both payment variants, including default/custom account prefixes.
It passed against the original DAO-local methods before extraction and passes
against the extracted methods with the same assertions. Together with the six
frozen JDBC snapshots and 17 existing DAO helper contracts, this is 25 passing
mapping cases before and after. No live database is used.
