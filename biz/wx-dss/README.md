https://blog.csdn.net/weixin_50185313/article/details/128130923
https://mp.weixin.qq.com/s/-0a52wZRUlA4ICHKuijvfg

https://blog.csdn.net/lslxgg/article/details/136100125
https://blog.csdn.net/bbill/article/details/135728409
https://blog.csdn.net/2302_76329106/article/details/134629237
https://blog.csdn.net/weixin_58572700/article/details/133513479
https://blog.csdn.net/qq_43291759/article/details/125926536
https://blog.csdn.net/ycmy2017/article/details/131358527
https://blog.csdn.net/weixin_53791978/article/details/132243551
https://blog.csdn.net/Hei_lovely_cat/article/details/124845461



https://blog.csdn.net/qq_37989738/article/details/107205863
https://blog.csdn.net/u012373815/article/details/54633046
https://blog.csdn.net/qq_30690165/article/details/131063119
https://blog.csdn.net/weiCong_Ling/article/details/134535173
https://blog.csdn.net/c18213590220/article/details/135595016
https://blog.csdn.net/jpgzhu/article/details/119991687
https://blog.csdn.net/weixin_38500202/article/details/118531444
https://blog.csdn.net/studyday1/article/details/131996303
https://blog.csdn.net/qq_45635939/article/details/136000757
https://blog.csdn.net/wangerxiao121223/article/details/122632133


docker run \
-p 9000:9000 \
-p 9001:9001 \
--name minio1 \
-v D:\minio\data:/data \
-e "MINIO_ROOT_USER=ROOTUSER" \
-e "MINIO_ROOT_PASSWORD=CHANGEME123" \
quay.io/minio/minio server /data --console-address ":9001"
