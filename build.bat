@echo off
REM Compiles every source file into out\.
REM Run from the root of the repository.

setlocal
cd /d "%~dp0"

set CP=lib\malibsonique.jar;lib\mesmaths_sources_avec_awt.jar;lib\malibrairiegui.jar

if not exist out mkdir out

dir /s /b exodecorateur_angryballs\*.java > out\sources.txt

javac -encoding UTF-8 -cp "%CP%" -d out @out\sources.txt
if errorlevel 1 (
    echo.
    echo Build failed.
    exit /b 1
)

del out\sources.txt
echo.
echo Build OK. Run run.bat to start the application.
endlocal
