var map = {};
map.key1 = "aaa";
map.key2 = "bbb";
map.key3 = "ccc";
map['key4'] = "ddd";
map['key5'] = "eee";
//遍历取得map中的值
for(var key in map){
    console.log("key : " + key + " value : " + map[key]);//控制台中打印
}
