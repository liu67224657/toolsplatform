function Slider(ops){
  this.outer=ops.wrapper;
  this.idx=ops.sildIdx;
  this.init();   // 初始化
  this.go(ops.sildIdx);
  this.bindDom();
}
Slider.prototype.init=function(){
   this.per=window.innerHeight/window.innerWidth;
    this.winw=window.innerWidth;
    this.idx=this.idx;
    this.lis=this.outer.getElementsByTagName('li');
    var iw=this.outer.getElementsByTagName('img');
    for(i=0;i<this.lis.length;i++){
      this.lis[i].style.webkitTransform='translate3d(750px,0,0)'
      this.lis[this.idx].style.webkitTransform='translate3d(0,0,0)';
    }
}

Slider.prototype.bindDom=function(){
    var self=this;
    var scale=this.winw;
    var outer=this.outer;
    var list=outer.getElementsByTagName('li');
    var starthander=function(evt){
      evt.preventDefault();
      self.startX=evt.touches[0].pageX;
      self.offsetX=0;
      self.startTime=new Date()*1;
    }
    var movehander=function(evt){
      evt.preventDefault();
      self.offsetX=evt.touches[0].pageX-self.startX;
      var p=self.idx-1;
      var c=p+3;
      for(p;p<c;p++){
       list[p] && (list[p].style.webkitTransform='translate3d('+((p-self.idx)*scale+self.offsetX)+'px,0,0)');
       list[p] && (list[p].style.webkitTransition='none');
      }
      
    }
    var endhander=function(evt){
         var boundary=scale/6; 
         var endTime=new Date()*1;
         var lis=outer.getElementsByTagName('li'); 
         if(endTime-self.startTime>800){
              if(self.offsetX>=boundary){
                self.go('-1');
              }else if(self.offsetX<=-boundary){
                 self.go('1');
              }else{
                 self.go('0');
              }
          }else{
            if(self.offsetX>50){
          self.go('-1');
          }else if(self.offsetX<-50){
          self.go('1');
          }else{
          self.go('0');
        }
         }
    }
    outer.addEventListener('touchstart',starthander,false)   
    outer.addEventListener('touchmove',movehander,false)
    outer.addEventListener('touchend',endhander,false)
}
  Slider.prototype.go=function(n){
       var idx=this.idx;
       var scale=this.winw;
       var cidx;
       var lis=this.outer.getElementsByTagName('li');
       var len=lis.length;
       if(typeof n=='number'){
          cidx=n;
       }else if(typeof n=='string'){
          cidx=idx+n*1;
       }
       if(cidx>len-1){
         cidx=len-1
       }else if(cidx<0){
         cidx=0;
       }
       this.idx=cidx;
       $('.large-nownum').text(this.idx+1);
       lis[cidx].style.webkitTransition='-webkit-transform 0s ease-out';
       lis[cidx-1] && (lis[cidx-1].style.webkitTransition='-webkit-transform 0s ease-out');
       lis[cidx+1] && (lis[cidx+1].style.webkitTransition='-webkit-transform 0s ease-out');
       lis[cidx].style.webkitTransform='translate3d(0,0,0)';
       lis[cidx-1] && (lis[cidx-1].style.webkitTransform='translate3d(-'+scale+'px,0,0)');
       lis[cidx+1] && (lis[cidx+1].style.webkitTransform='translate3d('+scale+'px,0,0)');
    }