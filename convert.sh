#!/bin/bash

if ((!($# == 1))); then
  >&2 echo "usage: convert.sh path/to/file.mscz"
  exit 1
fi

file="${1}"

# convert the .mscz file to converter.musicxml so the java program can parse it
musescore4portable -o "${file%.*}.musicxml" "${file}" >/dev/null 2>&1

chmod u+x /home/joren/Converter/xml\ to\ Ireal.jar

# call the java program with the newly generated file
/pad/naar/program.jar "${file%.*}.musicxml"
