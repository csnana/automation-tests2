#!/bin/bash
# @author : sajeev rajagopalan


echo " "
echo "#################### process started for Interpolation #################### "
echo " "

## Deprecated
##ROOT=$(dirname $(readlink -n $0))

ROOT="`dirname \"$0\"`"              # relative
ROOT="`( cd \"$ROOT\" && pwd )`"  # absolute and normalized
if [ -z "$ROOT" ] ; then
  # error; for some reason, the path is not accessible
  # to the script (e.g. permissions re-elevated after suid)
  exit 1  # fail
fi

echo ROOT= "$ROOT"

PROCESSOR="bash ${ROOT}/preprocess-import.sh"
TARGET=${ROOT}/../test-suite/target/test-classes

echo TARGET= "$TARGET"

here=$(pwd)

cd ${ROOT}/../test-suite/src/it/resources
features=$(find . -name \*.feature)

for f in ${features}; do
    ifolder=$(dirname ${f})
    ofolder=${TARGET}/${ifolder}
    mkdir -p ${ofolder}
    cd ${ifolder}
    feature=$(basename ${f})
    echo "processing $feature => ${ofolder}/${feature}"
    ${PROCESSOR} -i ${feature} -o ${ofolder}/${feature}
    cd -
done

echo " "
echo "#################### process ended for Interpolation #################### "
echo " "

cd ${here}