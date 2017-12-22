#!/bin/bash

if [ $# -ne 1 ]; then
    echo "You must specified the LL code in parameter"
    exit 1
fi

llvm-as-3.8 $1 -o=code-source.bc
lli-3.8 code-source.bc