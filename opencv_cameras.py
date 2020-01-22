#!/usr/bin/python3

import numpy as np

#need to have opencv2 library installed
import cv2 as cv
import datetime
from apscheduler.schedulers.background import BackgroundScheduler

cred_file = "creds.txt"
url = ""
username = ""
password = ""

seat1 = 0
seat1_index = 0
occupancy_threshold = 500

def main():
  global seat1

  stream = cv.VideoCapture()
  # fetch video stream (from camera)
  stream.open("http://" + username + ":" + password + "@" + url)

  if not stream.isOpened:
      print('Unable to open/access network stream: ' + url)
      exit(1)

  last_frame = None

  while True:
    ret, frame = stream.read()
    cv.imshow("ClearAllFrame", frame);

    frame_gray = cv.cvtColor(frame, cv.COLOR_BGR2GRAY)
    frame_blur = cv.GaussianBlur(frame_gray, (15,15), 0)

    if last_frame is None:
      last_frame = frame_blur

    differ = cv.absdiff(frame_blur, last_frame)
    r0, threshold = cv.threshold(differ, 15, 255, cv.THRESH_BINARY)
    cv.imshow("ThresholdAllFrame", threshold);

    x1, y1, w1, h1 = 420, 110, 200, 150

    # thresholded roi
    troi1 = threshold[y1:y1+h1,x1:x1+w1]
    # clear roi
    croi1 = frame[y1:y1+h1,x1:x1+w1]

    # print non zero pixels in troi1
    print(cv.countNonZero(troi1))

    cv.imshow("ClearSeat1Frame", croi1);
    cv.imshow("ThresholdSeat1Frame", troi1);

    last_frame = frame_blur

    if(cv.waitKey(1) & 0xFF == ord("q")):
      break

  stream.release()
  cv.destroyAllWindows()

def read_credentials():
  global url
  global username
  global password

  f = open(cred_file,"r")

  if not f:
    print("Cannot open file: " + cred_file)
    exit(1)

  lines=f.readlines()
  url = lines[0].rstrip()
  username = lines[1].rstrip()
  password = lines[2].rstrip()

  f.close()

def count_seat_occupancy():
  global seat1
  global seat1_index

  print(str(datetime.datetime.now()) + ": " + str(seat1))
  if(seat1 > occupancy_threshold):
    seat1_index += 1

  seat1 = 0

def post_seat_occupancy():
  global seat1_index

  # if seat is occupied more than 20 times (minutes) from 30, then it is occupied
  # here should be a post request to server, so it is saved to database
  if(seat1_index > 20):
    # occupied
    print(str(datetime.datetime.now()) + ": " + "Executing job" + ": occupied")
  else:
    # unoccupied
    print(str(datetime.datetime.now()) + ": " + "Executing job" + ": unoccupied")

  seat1_index = 0

if __name__ == "__main__":
  read_credentials()

  # triggers every 1 minute
  scheduler = BackgroundScheduler()
  scheduler.add_job(func=count_seat_occupancy, trigger="cron", minute="*/1")
  scheduler.start()

  # triggers every half an hour
  scheduler1 = BackgroundScheduler()
  scheduler1.add_job(func=post_seat_occupancy, trigger="cron", minute="*/30")
  scheduler1.start()

  main()

  scheduler.shutdown()
  scheduler1.shutdown()