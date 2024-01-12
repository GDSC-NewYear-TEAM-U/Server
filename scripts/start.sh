#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/app

echo "> Build 파일을 복사합니다."

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 새 애플리케이션에 실행 권한을 부여합니다."

chmod +x $REPOSITORY/Colot-0.0.1-SNAPSHOT.jar

IDLE_PROFILE=$(find_idle_profile)

echo "> 새 애플리케이션을 $IDLE_PROFILE 로 실행합니다."

nohup java -jar \
-Dspring.config.location=$REPOSITORY/config/application.yml,\
$REPOSITORY/config/application-$IDLE_PROFILE.yml \
-Dspring.profiles.active=$IDLE_PROFILE \
$REPOSITORY/Colot-0.0.1-SNAPSHOT.jar > $REPOSITORY/nohup.out 2>&1 &
