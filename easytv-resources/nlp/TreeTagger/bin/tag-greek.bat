@echo off

set BIN=".\bin"
set CMD=".\cmd"
set LIB=".\lib"

set TAGOPT=%LIB%\greek.par -token -lemma -sgml -no-unknown

perl %CMD%\tokenize.pl "%~1" | perl %CMD%\mwl-lookup-greek.perl | %BIN%\tree-tagger %TAGOPT% > "%~2"

REM if "%2"=="" goto label1
REM perl %CMD%\tokenize.pl "%~1" | perl %CMD%\mwl-lookup-greek.perl | %BIN%\tree-tagger %TAGOPT% > "%~2" %RES%
REM goto end

REM :label1
REM if "%1"=="" goto label2
REM perl %CMD%\tokenize.pl "%~1" | perl %CMD%\mwl-lookup-greek.perl | %BIN%\tree-tagger %TAGOPT%
goto end

:label2
echo.
echo Usage: tag-greek file {file}
echo.

:end

