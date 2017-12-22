#!/bin/bash

if [ $# -ne 1 ]; then
    echo "You must specified the IMP File Name in parameter"
    exit 1
fi

file=`basename $1`
filename="${file%.*}"
java -jar dist/INFO-F403-IMP.jar ./test/grammar/UnambiguousIMP.gram $1 -o "llvm/${filename}.ll"

cd llvm
bash compile.sh "${filename}.ll"
cd ../