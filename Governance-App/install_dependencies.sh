apt update -y
apt install default-jdk -y
groupadd -r wildfly
useradd -r -g wildfly -d /opt/wildfly -s /sbin/nologin wildfly
WILDFLY_VERSION=21.0.0.Final
apt install wget -y
wget https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz -P /tmp
tar xf /tmp/wildfly-$WILDFLY_VERSION.tar.gz -C /opt/
ln -s /opt/wildfly-$WILDFLY_VERSION /opt/wildfly
chown -RH wildfly: /opt/wildfly
mkdir -p /etc/wildfly
cp /opt/wildfly/docs/contrib/scripts/systemd/wildfly.conf /etc/wildfly/
cp /opt/wildfly/docs/contrib/scripts/systemd/launch.sh /opt/wildfly/bin/
sh -c 'chmod +x /opt/wildfly/bin/*.sh'
cp /opt/wildfly/docs/contrib/scripts/systemd/wildfly.service /etc/systemd/system/
#apt install systemd -y
#systemctl daemon-reload