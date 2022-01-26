import os
import cv2
import glob
import numpy as np
import scipy.spatial as sp
from handshape_feature_extractor import HandShapeFeatureExtractor
import shutil
from numpy import genfromtxt


def frameExtractor(videopath, frames_path, count):
    if not os.path.exists(frames_path):
        os.mkdir(frames_path)
    cap = cv2.VideoCapture(videopath)
    video_length = int(cap.get(cv2.CAP_PROP_FRAME_COUNT)) - 1
    frame_no = int(video_length / 2.4)
    cap.set(1, frame_no)
    ret, frame = cap.read()
    cv2.imwrite(frames_path + "/%#05d.png" % (count + 1), frame)


def getVectorData(files):
    hfe_instance = HandShapeFeatureExtractor.get_instance()
    vectorData = []
    videos = []
    count = 0
    for file in files:
        img = cv2.imread(file)
        img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        results = hfe_instance.extract_feature(img)
        results = np.squeeze(results)
        vectorData.append(results)
        videos.append(os.path.basename(file))
        count += 1

    return vectorData


def getGestureInfo(vector, penlayer_train_list):
    list = []
    for data in penlayer_train_list:
        list.append(sp.distance.cosine(vector, data))
        number = list.index(min(list))
    return number


def penultimateLayerData(path_to_frames, filename):
    path = os.path.join(path_to_frames, "*.png")
    frame_list = glob.glob(path)
    frame_list.sort()
    data = frame_list
    vector = getVectorData(data)
    np.savetxt(filename, vector, delimiter=",")


video_list = []
videos_path = os.path.join('traindata')
video_path = os.path.join(videos_path, "*.mp4")
video_list = glob.glob(video_path)
video_list = video_list

count = 0
for each in video_list:
    frames_path = os.path.join(os.getcwd(), "frames")
    frameExtractor(each, frames_path, count)
    count = count + 1
filename1 = 'trainingSet_penultimate.csv'
frames_path = os.path.join(os.getcwd(), "frames")
penultimateLayerData(frames_path, filename1)

video_list = []
videos_path = os.path.join('test')
video_path = os.path.join(videos_path, "*.mp4")
video_list = glob.glob(video_path)
video_list = video_list

count = 0
for each in video_list:
    frames_path = os.path.join(os.getcwd(), "frames")
    frameExtractor(each, frames_path, count)
    count = count + 1
filename2 = 'testSet_penultimate.csv'
frames_path = os.path.join(os.getcwd(), "frames")
penultimateLayerData(frames_path, filename2)

shutil.rmtree(os.path.join(os.getcwd(), "frames"))

training_data = genfromtxt(filename1, delimiter=",")
test_data = genfromtxt(filename2, delimiter=",")
res = []
for each in test_data:
    res.append(getGestureInfo(each, training_data))


np.savetxt("Results.csv", res, delimiter=",", fmt='% d')