# CSC2001-Project-1
Java Assignment that required working with data structures like arrays and binary search trees.
# write all steps to compile, run and test the project
This README explains how to compile, run, and test the project on Windows (PowerShell).
 
## 1. Prerequisites
 
## 2. Project Layout (important files)
 
- `src/` contains Java source files
- `data/SAPlaceNames.csv` dataset
- `data/SearchQueries.txt` query set (must contain 50 non-empty queries)
- `data/SAPlaceNamesOptimal.txt` optimal BST insertion order
 
## 3. Compile
 
Compile all Java files:
 
```powershell
javac src\*.java
```
 
If compilation succeeds, `.class` files will appear in `src/`.
 
Optional clean step (remove old class files before recompiling):
 
```powershell
Remove-Item src\*.class -Force
javac src\*.java
```
 
## 4. Run
 
### 4.1 Run the experiment driver (recommended)
 
From project root:
 
```powershell
java -cp src PlaceExperiment --csv ExperimentResults.csv
```
 
What this does:
 
- Runs experiments for N = 1000, 2000, ..., 10000
- Computes average comparisons for:
  - Array
  - BST(as-is)
  - BST(sorted)
  - BST(optimal)
- Prints a results table to the terminal
- Writes results to `ExperimentResults.csv`
 
You can choose another output file:
 
```powershell
java -cp src PlaceExperiment --csv my_results.csv
```
 
### 4.2 Run interactive array search app
 
```powershell
java -cp src PlaceSearchArray
```
 
Menu flow:
 
1. Choose `1` (Load dataset)
2. Enter filename, for example: `data/SAPlaceNames.csv`
3. Enter N, for example: `1000`
4. Choose `2` (Search place), enter place name
5. See result + comparison count
6. Choose `3` to quit
 
### 4.3 Run interactive BST search app
 
```powershell
java -cp src PlaceSearchBST
```
 
Use the same load/search flow as above.
 
## 5. Testing Guide
 
This project does not include automated unit tests, so testing is done by compile checks and functional runs.
 
### Test A: Build test
 
```powershell
javac src\*.java
```
 
Pass condition:
 
- Command finishes without Java compiler errors
 
### Test B: Experiment functional test
 
```powershell
java -cp src PlaceExperiment --csv ExperimentResults.csv
```
 
Pass conditions:
 
- Terminal prints header:
  - `N Array BST(as-is) BST(sorted) BST(optimal)`
- Terminal prints 10 data rows (for 1000 through 10000)
- CSV file is created
 
Quick file check:
 
```powershell
Test-Path ExperimentResults.csv
Get-Content ExperimentResults.csv | Select-Object -First 12
```
 
Expected:
 
- `Test-Path` returns `True`
- CSV contains 1 header row + 10 result rows
 
### Test C: Array search sanity test
 
```powershell
java -cp src PlaceSearchArray
```
 
Manual checks:
 
1. Load `data/SAPlaceNames.csv` with N = `1000`
2. Search a known place from the dataset (should return an entry)
3. Search `__NOT_A_REAL_PLACE__` (should print `Not found`)
4. Confirm a comparison count is printed each search
 
### Test D: BST search sanity test
 
```powershell
java -cp src PlaceSearchBST
```
 
Repeat the same checks as Test C.
 
## 6. Troubleshooting
 
- `Could not find input file: ...`
  - Run from project root, or ensure files exist in `data/`
- `SearchQueries.txt must contain exactly 50 non-empty queries.`
  - Fix `data/SearchQueries.txt` to contain exactly 50 non-empty lines
- `ClassNotFoundException`
  - Re-run compile command and ensure `-cp src` is used
- Compile errors after edits
  - Clean and rebuild:
 

 

 
