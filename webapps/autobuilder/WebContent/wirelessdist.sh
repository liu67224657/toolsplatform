#!/bin/csh -f

cd /Users/antony/Documents/autopackage/ios_builder/template/wirelessdist

cp ../build/ipas/jailbreak/* ./workspace/ios/
echo 'cp ../build/ipas/jailbreak/* ./workspace/ios/'

python distributionhtmlgenerate.py
echo 'python distributionhtmlgenerate.py'
cd ./workspace

python index.py 8888
echo 'python index.py'