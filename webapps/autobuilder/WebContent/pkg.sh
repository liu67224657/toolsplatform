#!/bin/csh -f

 
#get version
set version=`cat /opt/package/platform/zeroversion`

#cd /Users/ef2013/opt/ios_builder/template/shell
cd /Users/antony/Documents/autopackage/ios_builder/template/src

python project.py "$1" "$2"  "$3" "configfile" "$4" "$5"
