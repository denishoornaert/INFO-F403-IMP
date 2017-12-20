#!/bin/bash

if [ $# -ne 1 ]; then
    echo "Tu as oublié un argument gro"
    exit 1
fi

llvm-as-3.8 $1 -o=code-source.bc
lli-3.8 code-source.bc