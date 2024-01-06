from datetime import datetime as dt

from tkinter import *

import pytz as zone

 

rootWindow = Tk()

rootWindow.title("時鐘")

rootWindow.resizable(False, False)

timeText = StringVar()

localTimeText = StringVar()

label = Label(rootWindow, text="國家標準時間", font=('Segoe UI', 16)).grid(row=0, column=0)

localLabel = Label(rootWindow, text="中歐時間", font=('Segoe UI', 16)).grid(row=2, column=0)

timeLabel = Label(rootWindow, textvariable=timeText, font=('Segoe UI', 21))

localTimeLabel = Label(rootWindow, textvariable=localTimeText, font=('Segoe UI', 21))

 

def dayWeek(d):

    if d == 0:

        return "(週一)"

    elif d == 1:

        return "(週二)"

    elif d == 2:

        return "(週三)"

    elif d == 3:

        return "(週四)"

    elif d == 4:

        return "(週五)"

    elif d == 5:

        return "(週六)"

    elif d == 6:

        return "(週日)"

 

def twHourFormat(h):

    if h == 0:

        return "凌晨12"

    elif h > 0 and h <= 5:

        return "凌晨" + str(h)

    elif h == 12:

        return "中午12"

    elif h > 12:

        return "下午" + str(h - 12)

    else:

        return "上午" + str(h)

 

def zero(time):

    if len(str(time)) == 1:

        return "0" + str(time)

    else:

        return str(time)

    

def update():

    nowLocal = dt.now(zone.timezone("Europe/Madrid"))

    now = dt.now(zone.timezone("ROC"))

    timeText.set("民國" + str(now.year - 1911) + "年" + zero(now.month) + "月" + zero(now.day) + "日" + dayWeek(now.weekday()) + ". " + twHourFormat(now.hour) + "點" + zero(now.minute) + "分" + zero(now.second) + "秒")

    localTimeText.set(str(nowLocal.year) + "年" + zero(nowLocal.month) + "月" + zero(nowLocal.day) + "日" + dayWeek(nowLocal.weekday()) + ". " + zero(nowLocal.hour) + "點" + zero(nowLocal.minute) + "分" + zero(nowLocal.second) + "秒")

    rootWindow.after(1001, update)

update()

timeLabel.grid(row=1, column=0)

localTimeLabel.grid(row=3, column=0)

rootWindow.mainloop()

