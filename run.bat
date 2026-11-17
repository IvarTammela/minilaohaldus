@echo off
rem Paneb konsooli UTF-8 peale (täpitähed ja raamid), kompileerib ja käivitab.
chcp 65001 >nul
javac -encoding UTF-8 -d out src\*.java || exit /b 1
java -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stdin.encoding=UTF-8 -cp out Main
