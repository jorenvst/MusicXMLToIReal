#!/bin/bash

if ((!($# == 1))); then
  >&2 echo "usage: convert.sh path/to/file.mscz"
  exit 1
fi

file="${1}"

# convert the .mscz file to converter.musicxml so the java program can parse it

# TE VERANDEREN: musescore4portable moet je vervangen door de naam van het commando voor musescore
musescore4portable -o "${file%.*}.musicxml" "${file}" >/dev/null 2>&1

chmod u+x /home/joren/Converter/xml\ to\ Ireal.jar

# call the java program with the newly generated file

# TE VERANDEREN
/pad/naar/program.jar "${file%.*}.musicxml"
