#!/bin/bash
# @author : sajeev rajagopalan



##    *------------------------------------------------------------------------------------
##   |  Method PROCESS IMPORT
##   |  Purpose:  Import all the csv values to the userstory in runtime.
##   |  Pre-condition: Preprocess-all-feature must be called & arguments should be passed.
##   |  Mac OS X : brew install gnu-sed
##   |  Post-condition: Maven Integration test should be called.
##   |  Parameters: See usage
##   |  Returns: Process & interpolate the feature with CSV info.
##   *--------------------------------------------------------------------------------------


set -euo pipefail

GSED=sed
[ `uname` == "Darwin" ] && GSED=gsed

function usage() {
    cat <<END >&2
USAGE: $0 [-i input] [-o output]
        -i input       # input file
        -o output      # output file. if not passed, prints out to console
        -h|?           # usage
        -v             # verbose

eg,
     $0 -i WebService.feature -o target/WebService.feature
END
    exit $1
}

declare input=""
declare output=""
declare -i DO_VERB=0

while getopts "i:o:hv" opt
do
    case ${opt} in
        i) input=${OPTARG};;
        o) output=${OPTARG};;
        v) DO_VERB=1;;
        h|?) usage 0;;
        *) usage 1;;
    esac
done

[ ! -z "${input}" ] || { echo >&2 " input required"; usage 1; }
[ -f "${input}" ] || { echo >&2 " unable to read input file: $input"; exit 2; }

#declare -r imports=$(grep -v '^\s*#' ${input} | egrep  '<<(.+)>>' | tr -d '<>' | sort -u)
declare -r imports=$(egrep  '#<<(.+)>>' ${input} | tr -d ' #<>' | sort -u)

filename=$(basename ${input})
tmpfile="/tmp/${filename}"

cp -f "${input}" "${tmpfile}.0"

function get_content() {
    cfile=`basename ${1}.tmp`
    cfile="../csv/$cfile"
    cp -f ../csv/${1} ${cfile}
    declare -i c=0
    bamboo_vars=$(env | grep -i BAMBOO_)
    for kv in ${bamboo_vars}; do
        k=$(echo ${kv} | awk -F= '{print $1}' | cut -d'_' -f2-);
        v=$(echo ${kv} | awk -F= '{print $2}' | tr -d \");
        echo >&2 "$k => $v"
        ((c+=1))
        ${GSED} -i -e "s/\${$k}/$v/Ig" ${cfile}
    done
    #perl -pe "s/\n/|\\\n/g;s/^/|/g;s/,/|/g" ${cfile}
    perl -pe "s/\n/|\\\n/g;s/^/|/g;s/,/|/g" ${cfile}
    rm -f ${cfile} 2>/dev/null
}

declare -i c=0
for i in ${imports}; do
    [ -f "../csv/${i}" ] || { echo >&2 " not found import file: '${i}'"; continue; }
    declare content=$(get_content ${i})
    pc=${c}
    ((c+=1))
    ${GSED} -e "s/#<<${i}>>/${content}/g" "${tmpfile}.${pc}" > "${tmpfile}.${c}"
    /bin/rm "${tmpfile}.${pc}"
done

if [ -z "${output}" ]; then
    cat "$tmpfile.${c}"
    /bin/rm "${tmpfile}.${c}"
else
    mv -f "$tmpfile.${c}" "${output}"
fi
