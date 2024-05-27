#!/bin/bash

if ((!($# == 1))); then
  exit 1
fi

file="${1}"
# convert the .mscz file to musicxml so the java program can parse it
musescore4portable -o "${file%.*}.musicxml" "${file}" >/dev/null 2>&1

# call the java program with the newly generated file
chmod u+x /home/joren/Converter/xml\ to\ Ireal.jar
/home/joren/Converter/xml\ to\ Ireal.jar "${file%.*}.musicxml"
