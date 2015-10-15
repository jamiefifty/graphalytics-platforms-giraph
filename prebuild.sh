#!/bin/sh
#
# Copyright 2015 Delft University of Technology
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#         http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

granulaPatch=`pwd`/`ls -td granula*.patch`

# install granula
rm -Rf /tmp/granula
git clone https://github.com/tudelft-atlarge/granula /tmp/granula
cd /tmp/granula

git checkout pre-alpha

cd /tmp/granula/granula-modeller/giraph/1.1.0/analyzer
mvn clean install -DskipTests

# install patched graph processing platform
rm -Rf /tmp/giraph
git clone https://github.com/apache/giraph /tmp/giraph
cd /tmp/giraph; 

git checkout release-1.1.0
git checkout -b patching

git apply -v $granulaPatch

git status

mvn clean install -Phadoop_2 -Dhadoop.version=2.5.1 -DskipTests