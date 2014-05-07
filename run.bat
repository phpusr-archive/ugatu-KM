call set-env.bat

set NAME=KM-assembly-0.1-SNAPSHOT

rem call scala target\scala-2.10\%NAME%.jar experiment.form.MainForm
java -jar target\scala-2.10\%NAME%.jar
pause