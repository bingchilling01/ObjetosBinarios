from datetime import datetime as dt
from tkinter import *
import pytz as zone
 
z = True
rootWindow = Tk()
rootWindow.title("時鐘")
rootWindow.resizable(False, False)
timeText = StringVar()
localText = StringVar()
 
timeLabel = Label(rootWindow, textvariable=timeText, font=('Segoe UI', 21))
 
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
    label = Label(rootWindow, textvariable=localText, font=('Segoe UI', 16)).grid(row=1)
    timeLabel.grid(row=2)
    global z
    
    if z:
        localText.set("國家標準時間")
        now = dt.now(zone.timezone("ROC"))
        timeText.set("民國" + str(now.year - 1911) + "年" + zero(now.month) + "月" + zero(now.day) + "日" + dayWeek(now.weekday()) + ". " + twHourFormat(now.hour) + "點" + zero(now.minute) + "分" + zero(now.second) + "秒")
    else:
        localText.set("中歐標準時間")
        nowLocal = dt.now(zone.timezone("Europe/Madrid"))
        timeText.set(str(nowLocal.year) + "年" + zero(nowLocal.month) + "月" + zero(nowLocal.day) + "日" + dayWeek(nowLocal.weekday()) + ". " + zero(nowLocal.hour) + "點" + zero(nowLocal.minute) + "分" + zero(nowLocal.second) + "秒")
    
    rootWindow.after(1001, update)
 
def changeZone():
    global z
    if not z:
        z = True
    else:
        z = False
    update()
 
def changeButton():
    tzButton = Button(rootWindow, text="更換", font=('Segoe UI', 10), padx=20, command=changeZone).grid(row=0)   
 
initButton = Button(rootWindow, text="開始", font=('Arial', 18), padx=30, command=lambda: [update(), changeButton(), initButton.destroy()])
initButton.grid(row=0)
 
rootWindow.mainloop()