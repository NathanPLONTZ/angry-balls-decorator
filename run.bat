@echo off
REM Starts the application.
REM Must run from the root of the repository: the audio folder is resolved
REM relative to the working directory, as exodecorateur_angryballs\maladroit\bruits.

setlocal
cd /d "%~dp0"

if not exist out\exodecorateur_angryballs (
    echo Nothing compiled yet. Run build.bat first.
    exit /b 1
)

set CP=out;lib\malibsonique.jar;lib\mesmaths_sources_avec_awt.jar;lib\malibrairiegui.jar

java -Dfile.encoding=UTF-8 -cp "%CP%" exodecorateur_angryballs.maladroit.TestAngryBalls
endlocal
