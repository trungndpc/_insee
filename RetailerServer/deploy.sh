local_dir=./
server_dir=/home/stackops/home/cuahang/webapp

server=stackops@61.28.229.63
jar_file=RetailerServer-1.0-SNAPSHOT.jar

rsync -a $local_dir/target/$jar_file $server:$server_dir/jar/