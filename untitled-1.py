def submax(lst,i,j):
    if i==j:
        return [lst[i],i,j]
    if j<i:
        return None
    mid = (i+j)// 2
    #print(i,j,mid)
    #rightlst = lst[i:mid]
    #leftlst=lst[mid+1:j+1]
    
    lp=submax(lst,i,mid-1) 
    #if lp is not None:
     #   print("l:",i,mid-1)
      #  print(lp)
    rp=submax(lst,mid+1,j)
    #print("r:",mid+1,j)
    #print(rp)
    
    lm=0
    if lp is not None:
        #lm = lp[0]
        for k in range(lp[1],mid):
            lm = lm + lst[k]
            
    rm = 0
    for k in range(mid+1,rp[2]+1):
        rm = rm+lst[k]
    
    curmax = lst[mid]
    curi = mid
    curj = mid
    if lm>0:
        curmax = curmax+lm
        curi = lp[1]
    if rm>0:
        curmax = curmax + rm
        curj = rp[2]
    
    max = curmax
    if (lp is not None) and lp[0]>max:
        curi=lp[1]
        curj = lp[2]
    if (rp is not None) and rp[0]>max:
        curi=rp[1]
        curj=rp[2]
    
    print(max,curi,curj)
    return [max,curi,curj]