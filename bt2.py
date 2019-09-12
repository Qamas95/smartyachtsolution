import bluetooth
import struct
import threading
import time
import RPi.GPIO as GPIO        #calling for header file which helps in using GPIOs of PI
from w1thermsensor import W1ThermSensor
LED=21
Heater=20
Anchor=16
sensor = W1ThermSensor()
GPIO.setmode(GPIO.BCM)     #programming the GPIO by BCM pin numbers. (like PIN40 as GPIO21)
GPIO.setwarnings(False)
GPIO.setup(LED,GPIO.OUT)  #initialize GPIO21 (LED) as an output Pin
GPIO.setup(Heater,GPIO.OUT)
GPIO.setup(Anchor,GPIO.OUT)
GPIO.output(LED,0)
GPIO.output(Heater,0)
GPIO.output(Anchor,0)
server_socket=bluetooth.BluetoothSocket( bluetooth.RFCOMM )
 
port = 1
server_socket.bind(("",port))
server_socket.listen(1)
 
client_socket,address = server_socket.accept()
print "Accepted connection from ",address



def background():
    while True:
          data = client_socket.recv(1024)
          print "Received: %s" % data
          if (data == "0"):    #if '0' is sent from the Android App, turn OFF the LED
           print ("GPIO 21 LOW, LED OFF")
           GPIO.output(LED,0)
          if (data == "1"):    #if '1' is sent from the Android App, turn OFF the LED
           print ("GPIO 21 HIGH, LED ON")
           GPIO.output(LED,1)
          if (data == "01"):    
           print ("GPIO 20 LOW, Heater OFF")
           GPIO.output(Heater,0)
          if (data == "10"):    
           print ("GPIO 20 HIGH, Heater ON")
           GPIO.output(Heater,1)
          if (data == "011"):    
           print ("GPIO 16 LOW, Anchor Down")
           GPIO.output(Anchor,0)
          if (data == "100"):    
           print ("GPIO 16 HIGH, Anchor Up")
           GPIO.output(Anchor,1)
          if (data == "q"):
           print ("Quit")
       
               
def foreground():
    while True:
          temperature = sensor.get_temperature()
          temperature = struct.pack('!d', 3.1415)
          client_socket.send(temperature)
      


b = threading.Thread(name='background', target=background)
f = threading.Thread(name='foreground', target=foreground)

 
while 1:
  b.start()
  f.start()
  time.sleep(5)
  break
 
client_socket.close()
server_socket.close()
