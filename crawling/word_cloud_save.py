# get img
img_path = './word_cloud.png' # set Image path

from PIL import Image
img = Image.open(img_path)

print("get img ok")

# save to hdfs
hdfs_path = 'http://cluster.p.ssafy.io:9870'
hdfs_img_path = '/user/j8a509/wordcloud' # check hdfs directory path

from hdfs import InsecureClient

print("saving...")
client = InsecureClient(hdfs_path, user='j8a509')
client.upload(hdfs_img_path, img_path, overwrite=True)
print("save ok")
