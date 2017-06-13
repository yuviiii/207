from pylab import *
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.cbook as cbook
import random
import time
from scipy.misc import imsave
from scipy.misc import imread
from scipy.misc import imresize
import matplotlib.image as mpimg
import os
import shutil
from scipy.ndimage import filters
import urllib

def part2_separate(names):
    if not os.path.exists("validation_set"):
        os.makedirs("validation_set")
    else:
        shutil.rmtree("validation_set")
        os.makedirs("validation_set")
    if not os.path.exists("training_set"):        
        os.makedirs("training_set")
    else:
        shutil.rmtree("training_set")
        os.makedirs("training_set")
    if not os.path.exists("test_set"):
        os.makedirs("test_set")
    else:
        shutil.rmtree("test_set")
        os.makedirs("test_set")
    
    image_files=os.listdir("cropped/")
    
    for n in names:
        name=n.split()[1].lower()
        count = 0
        for img in image_files:
            if img.startswith(name):
                if count<100:
                    shutil.copy("cropped/"+img,"training_set/")
                elif count<110:
                    shutil.copy("cropped/"+img,"validation_set/")
                elif count<120:
                    shutil.copy("cropped/"+img,"test_set/")
                count += 1


def get_data(names1,names2,filename,size):
    alltraining=os.listdir(filename)
    data=[]
    y=[]
    for name1 in names1:
        counter=0
        for  img in alltraining:
            if counter<size and img.startswith(name1):
                person1_data=imread(filename+"/"+img)
                data.append([1]+array(person1_data).flatten().tolist())
                y+=[1]
                counter+=1
    for name2 in names2:
        counter=0
        for img in alltraining:
            if counter<size and img.startswith(name2):
                person2_data=imread(filename+"/"+img)
                data.append([1]+array(person2_data).flatten().tolist())
                y+=[0]
                counter+=1
    all_data=array(data)

    return all_data,array(y)

def newf(x,y,theta):
    return sum((dot(x,theta)-y)**2)

def newdf(x,y,theta):
    return (dot(x.T,(dot(x,theta)-y))*2)

def grad_descent_matrix(f, df, x, y, init_t, alpha):
    EPS = 1e-8
    prev_t = init_t-10*EPS
    t = init_t.copy()
    max_iter = 10000
    iter  = 0
    while norm(t - prev_t) >  EPS and iter < max_iter:
        prev_t = t.copy()
        t -= alpha*df(x, y, t)
        iter += 1
    print "iter of gd",iter
    return t

def f(x, y, theta):
    return sum( (y - dot(x,theta)) ** 2)/(x.shape[0]*2)

def df(x, y, theta):
    return -dot(x.T,(y-dot(theta.T,x.T)))/(x.shape[0])


def grad_descent(f, df, x, y, init_t, alpha):
    EPS = 1e-8
    prev_t = init_t-10*EPS
    t = init_t.copy()
    max_iter = 30000
    iter  = 0
    while norm(t - prev_t) >  EPS and iter < max_iter:
        prev_t = t.copy()
        t -= alpha*df(x, y, t)
        iter += 1
    return t

def part3(name1,name2,filename):
    x,y=get_data(name1,name2,filename,100)
    theta0=array([0.]*1025)

    return x,grad_descent(f, df, x, y, theta0, 1e-7),y

def check(arr,boundary):

    count1=0
    count2=0
    for i in range(0,arr.shape[0]//2):
        if arr[i]>=boundary:
            count1+=1
    for i in range(arr.shape[0]//2,arr.shape[0]):
        if arr[i]<boundary:
            count2+=1 
    print count1, count2
    
def get_data_matrix(names,filename):
    alltraining=os.listdir(filename)
    data=[]
    y=[]
    for i in range(0,len(names)):
        name=names[i]
        for img in alltraining:
            if img.startswith(name):
                person_data=imread(filename+"/"+img)
                data.append([1]+array(person_data).flatten().tolist())
                out=[0]*(len(names))
                out[i]=1
                y.append(out)
    all_data=array(data)
    print all_data.shape
    return all_data,array(y)

def limit(x,y,theta,a,b):
    h=1e-8
    list_h=[[0,0,0],[0,0,0],[0,0,0]]
    list_h[a][b]=h
    return (newf(x,y,theta+array(list_h))-newf(x,y,theta-array(list_h)))/(2*h)
    
def part6d(a,b):
    test_x=array(([3,2,1],[2,1,2],[0,1,1],[2,1,0]))
    test_y=array(([1,0,0],[0,1,0],[1,0,1],[0,0,1]))
    init_theta=array([[0.]*3]*3)
    theta=grad_descent(newf,newdf,test_x,test_y,init_theta,1e-7)
    print "using limit"
    print limit(test_x,test_y,theta,a,b)
    print "using gradient descent"
    print newdf(test_x,test_y,theta)[a][b]

def part7():
    names=['drescher', 'ferrera', 'chenoweth', 'baldwin', 'hader', 'carell']
    x,y=get_data_matrix(names,"training_set")
    theta0=array([[0.]*6]*1025)
    return x,grad_descent_matrix(newf,newdf,x,y,theta0,1e-11)

def check_matrix(arr):
    print arr.shape
    result=[0]*arr.shape[1]
    count=0
    k=0
    for i in range(arr.shape[0]):
        if arr[i][k]==max(arr[i]):
            result[k]+=1
        count+=1
        if count==(arr.shape[0]//arr.shape[1]):
            count=0
            k+=1
    print result

def part4_two_image():
    x,y=get_data(["hader"],["carell"],"training_set",2)
    theta0=array([0.]*1025)
    theta=grad_descent(f,df,x,y,theta0,1e-8)
    t=theta[1:].reshape(32,32)
    fig2=figure(2)
    ax2=fig2.gca()
    graph=ax2.imshow(t,cmap=cm.coolwarm)
    fig2.colorbar(graph)
    show()

if __name__=="__main__":
    act =['Fran Drescher', 'America Ferrera', 'Kristin Chenoweth',
          'Alec Baldwin', 'Bill Hader', 'Steve Carell']
    act_test = ['Gerard Butler', 'Daniel Radcliffe', 'Michael Vartan',
                'Lorraine Bracco', 'Peri Gilpin', 'Angie Harmon']
    
    #part2_separate(act+act_test);
    
    #part3
    x,theta,y=part3(["hader"],["carell"],"training_set")
    validation_data,val_y=get_data(["hader"],["carell"],"validation_set",10)
    check(dot(x,theta),0.5)
    check(dot(validation_data,theta),0.5)
    print "training" , f(x,y,theta)
    print f(validation_data,val_y,theta)
    
    #part4    
    t=theta[1:].reshape(32,32)
    fig=figure(1)
    ax=fig.gca()
    graph=ax.imshow(t,cmap=cm.coolwarm)
    fig.colorbar(graph)
    show()
    
    part4_two_image()
    
    #part5
    #If you want to use different size of training sets, just modify the size.
    size=25
    all_data,y=get_data(["drescher", "ferrera", "chenoweth"],
                      ['baldwin', 'hader', 'carell'],"training_set",size)
    theta0=array([0.]*1025)
    theta=grad_descent(f, df, all_data, y, theta0, 1e-7)
    validation_data=get_data(["drescher", "ferrera", "chenoweth"],
                      ['baldwin', 'hader', 'carell'],"validation_set",10)[0]
    check(dot(all_data,theta),0.5)  
    check(dot(validation_data,theta),0.5)
    test_data=get_data(['bracco', 'gilpin', 'harmon'],
                       ['butler', 'radcliffe', 'vartan'],"training_set",100)[0]
    check(dot(test_data,theta),0.5)
    
    #part6
    part6d(1,2)  
    part6d(1,1)
    part6d(0,2)
    
    #part7
    x,theta_part7=part7()
    check_matrix(dot(x,theta_part7))
    validation_data=get_data_matrix(['drescher', 'ferrera', 'chenoweth',
                                     'baldwin', 'hader', 'carell'],
                                    "validation_set")[0]
    check_matrix(dot(validation_data,theta_part7))
    
    #part8
    #descher
    t_des=(theta_part7.T)[0][1:].reshape(32,32)
    fig3=figure(3)
    ax3=fig3.gca()
    graph3=ax3.imshow(t_des,cmap=cm.coolwarm)
    fig3.colorbar(graph3)
    show()
    
    t_des=(theta_part7.T)[1][1:].reshape(32,32)
    fig4=figure(4)
    ax4=fig4.gca()
    graph4=ax4.imshow(t_des,cmap=cm.coolwarm)
    fig4.colorbar(graph4)
    show()    
    
    t_des=(theta_part7.T)[2][1:].reshape(32,32)
    fig5=figure(5)
    ax5=fig5.gca()
    graph5=ax5.imshow(t_des,cmap=cm.coolwarm)
    fig5.colorbar(graph3)
    show()
    
    t_des=(theta_part7.T)[3][1:].reshape(32,32)
    fig6=figure(6)
    ax6=fig6.gca()
    graph6=ax6.imshow(t_des,cmap=cm.coolwarm)
    fig6.colorbar(graph6)
    show()
    
    t_des=(theta_part7.T)[4][1:].reshape(32,32)
    fig7=figure(7)
    ax7=fig7.gca()
    graph7=ax7.imshow(t_des,cmap=cm.coolwarm)
    fig7.colorbar(graph7)
    show()    
    
    t_des=(theta_part7.T)[5][1:].reshape(32,32)
    fig8=figure(8)
    ax8=fig8.gca()
    graph8=ax8.imshow(t_des,cmap=cm.coolwarm)
    fig8.colorbar(graph8)
    show()    
