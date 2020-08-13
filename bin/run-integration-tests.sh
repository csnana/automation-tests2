#!/bin/bash
# @ author - sajeev rajagaopalan
# this is the trigger script for execution, please do not modify ***



declare -r SOURCE="${BASH_SOURCE[0]}"
declare -r DIR=`dirname ${SOURCE}`
declare -r ROOT="$DIR/.."
Report_Path="`( cd \"$ROOT\" && pwd )`"  # absolute and normalized
if [ -z "$Report_Path" ] ; then
  # error; for some reason, the path is not accessible
  # to the script (e.g. permissions re-elevated after suid)
  exit 1  # fail
fi



function usage() {
    cat <<END >&2
USAGE: $0 [-e env] [-t tags] [-b firefox] [-f file] [-d k=v,k2=v2] [-x|-r|-l|-c|-i|-h|-o|-v|-s|-n]
        -e env         # environment: local, int, acp, stg, prod
        -t tags        # comma seperated list of tags, e.g, @webservice,~@wip
        -f file        # key=value file to interpolate feature data files
        -b browser     # selects browser type (Desktop, Mobile simulators[IOS & Android])
        -r             # video recording enabled
        -l             # list available features, tags and import files
        -n             #list available tags alone
        -c             # clean before test
        -i             # interpolate only. don't run tests
        -I             # interpolate using agnostic soln
        -o             # open reports
        -v             # verbose
        -x             # headless run with Xvfb
        -u             # remote env url
        -D             # DriverTimeout in seconds
        -h|?           # usage

eg,
     $0 -e local
END
    exit $1
}


declare browser='default'
declare env='local'
declare goal='verify'
declare kvFile=''
declare kvData=''
declare recordVideo='false'
declare -i DO_LIST=0
declare -i DO_TAGS=0
declare -i DO_CLEAN=0
declare profiles="integration-tests"
declare -i DO_IP=0
declare -i DO_XVFB=0
declare -i DO_BOOTSTRAP=0
declare -i DO_JBOSS=0
declare -i DO_VERB=0
declare -i DO_OPEN_REPORT=0
declare opt
declare tags=''
declare url=''
declare timeOut=''
declare OPEN=open
[ $(uname -s) == "Cygwin" ] && OPEN=cygstart


while getopts "e:b:t:u:f:d:D:xvrholcisBjnI" opt
do
    case ${opt} in
        b) browser=${OPTARG};;
        e) env=${OPTARG};;
        t) tags=${OPTARG};;
        u) url=${OPTARG};;
        D) timeOut=${OPTARG};;
        f) kvFile=${OPTARG};;
        d) kvData=${OPTARG};;
        r) recordVideo='true';;
        l) DO_LIST=1;;
        s) goal='generate-test-resources';;
        c) DO_CLEAN=1;;
        i) profiles="integration-tests,import-features";;
        I) profiles="integration-tests,csv-generator";;
        o) DO_OPEN_REPORT=1;;
        B) DO_BOOTSTRAP=1;DO_JBOSS=1;;
        j) DO_JBOSS=1;;
        v) DO_VERB=1;;
        x) DO_XVFB=1;;
        n) DO_TAGS=1;;
        h|?) usage 0;;
        *) usage 1;;
    esac
done

if [ ${DO_LIST} -ne 0 ]; then
    printf "Avialable Features:\n"
    find ${ROOT}/test-suite/src -name "*.feature" -type f | rev | cut -d/ -f 1 | rev | sort -u | nl
    printf "\nAvialable tags:\n"
    find ${ROOT}/test-suite/src -name "*.feature" -type f | xargs egrep -h "^\s*@" | awk '{print $1}' | sort -u | nl
    printf "\nAvialable imports:\n"
    find ${ROOT}/test-suite/src -name "*.feature" -type f | xargs fgrep -h "#<<" | tr -d '#<>' | awk '{print $1}' | sort -u | nl
    exit 0
fi



if [ ${DO_TAGS} -ne 0 ]; then
    find ${ROOT}/test-suite/src -name "*.feature" -type f | xargs egrep -h "^\s*@" | awk '{print $1}' | sort -u | nl
    exit 0
fi



declare testEnvironmentURL='http://localhost/'


case "${env}" in
    PROD|prod|production)
        testEnvironmentURL='prod'
        ;;

    ST|st|staging)
        testEnvironmentURL='st'
        ;;

    SI|si|integration)
        testEnvironmentURL='si'
        ;;

    INT|int|integration)
        testEnvironmentURL='https://www.google.com.au'
        ;;

    local)
        testEnvironmentURL=''
        ;;
esac


declare testBrowser='ios'

case "${browser}" in

    IOSS|ios|ios)
    testBrowser='ios'
        ;;

    IOSSAPP|iosapp|iosApp)
    testBrowser='iosApp'
        ;;

    DROID|droid|droid)
    testBrowser='droid'
        ;;

    default)
        testBrowser='ios'
        ;;
esac




declare cucumberOptions='--tags ~@ignore --tags ~@wip --tags ~@broken'

if [ ! -z "${tags}" ]; then
    cucumberOptions+=" --tags ${tags}"
fi

declare bambooEnvUrl=''

if [ ! -z "${url}" ]; then
    bambooEnvUrl+="${url}"
  export bambooEnvUrl=${bambooEnvUrl}
fi

declare driverTimeout=''
if [ ! -z "${timeOut}" ]; then
   driverTimeout+="${timeOut}"
  export driverTimeout=${driverTimeout}
fi



if [ ! -z "${kvFile}" ]; then
    keys=$(awk -F= '{print $1}' ${kvFile})
    for k in ${keys}; do
        v=$(fgrep ${k} ${kvFile} | awk -F= '/${k}=/{print $2}')
        export BAMBOO_${k}=${v}
    done
fi

if [ ! -z "${kvData}" ]; then
    keys=$(echo ${kvData} | tr ',' '\n' | awk -F= '{print $1}')
    for k in ${keys}; do
        v=$(echo ${kvData} | tr ',' '\n' | grep ${k} | awk -F= '{print $2}')
        echo "$k => $v"
        export BAMBOO_${k}=${v}
    done
fi

[ "${DO_CLEAN}" -eq 0 ] || mvn -f ${ROOT}/pom.xml clean

export testEnvironmentUrl=${testEnvironmentURL}

    echo ""
    echo "[Appium-Start] --------------------------------------------------------------------"
    echo " ENV  : ${testEnvironmentURL}"
    echo " MODE : ${testBrowser}"
    echo "[Appium-Start] --------------------------------------------------------------------"
    echo ""

# trigger test
  mvn -f ${ROOT}/pom.xml -e -P${profiles} -pl test-suite  -Dcucumber.options="${cucumberOptions}" -DtestBrowser=${testBrowser} -DrecordVideo=${recordVideo} ${goal}


#spits out the maven job error code to determine failure ; will not work until we throws exception
declare -r MVN_EXIT=$?
echo "Maven exit code: $MVN_EXIT"

if [ "${DO_OPEN_REPORT}" -ne 0 ]; then
    if [ "$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" ]; then
        cd ${Report_Path}/test-suite/target/extent-reports/
        explorer index.html
    else
         ${OPEN} ${ROOT}/test-suite/target/extent-reports/index.html
    fi

fi


exit ${MVN_EXIT}

