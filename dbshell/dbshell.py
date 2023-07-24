import requests

host: str = input("Login Host: ")
port: str = input("Login Port: ")
user: str = input("Login User: ")
passwd: str = input("Login Passwd: ")

print("=======================================")
while True:
    command = input("MetaLite> ")
    get = requests.get(host+":"+port+"/"+user+"/"+passwd+"/"+command)
    a = get.text.split("\n")
    for i in a:
        print(" - "+i)
