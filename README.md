# 바로나 캡슐 API

바로나 캡슐 Repository : [barona-bixby-capsule](https://github.com/bactoria/barona-bixby-capsule)

&nbsp;

검색 가능한 키즈 채널 목록을 알려주는 기능

```ruby
Request :: GET
/kidsChannels


Response
[
  {
    "channelId": "UCKqx9r4mrFglauNBJc1L_eg",
    "title": "[토이푸딩] ToyPudding TV",
    "subscriber": "2550만",
    "imageUrl": "https://yt3.ggpht.com/a/AGF-l7-1D7hB_0rOExGjMxCzka-BffGT2-E86t79kA=s900-c-k-c0xffffffff-no-rj-mo"
  },
  {
    "channelId": "UCU2zNeYhf9pi_wSqFbYE96w",
    "title": "Boram Tube Vlog [보람튜브 브이로그]",
    "subscriber": "2060만",
    "imageUrl": "https://yt3.ggpht.com/a/AGF-l7-mJsuDHo09nNAqIn9YIgs0Lzc61G5UlvlUaw=s900-c-k-c0xffffffff-no-rj-mo"
  },
  # ...
}
```

```ruby
Request :: GET
/kidsChannels/page?size=1


Response
{
  "content": [
    {
      "channelId": "UCKqx9r4mrFglauNBJc1L_eg",
      "title": "[토이푸딩] ToyPudding TV",
      "subscriber": "2550만",
      "imageUrl": "https://yt3.ggpht.com/a/AGF-l7-1D7hB_0rOExGjMxCzka-BffGT2-E86t79kA=s900-c-k-c0xffffffff-no-rj-mo"
    }
  ],
  "pageable": { 
    #...
  }
}

```



&nbsp;

동영상 검색

```ruby
Request :: GET
/search?userId=1&searchData=뽀로로


Response
{
  "thumbnailUrl": "https://i.ytimg.com/vi/NFzO44aXYB0/hqdefault.jpg",
  "title": "뽀로로와노래해요 24시간 이어보기 | 뽀로로 인기동요 | 뽀로로 노래",
  "videoId": "vnd.youtube:NFzO44aXYB0"
}
```

&nbsp;

다음 비디오 

```ruby
Request :: GET
/nextVideo?userId=1


Response
{
  "thumbnailUrl": "https://i.ytimg.com/vi/xlmPbjAcRXQ/hqdefault.jpg",
  "title": "뽀로로 추석특집 | 뽀로로 이야기 ★3시간 이어보기!!★ | 귀경길 차 안에서! 집에서! 어디서든 함께해요!",
  "videoId": "vnd.youtube:xlmPbjAcRXQ"
}
```

```ruby
Request :: GET
/nextVideo?userId=2


Response
{
  "message": "다음 동영상이 존재하지 않습니다.",
  "status": 404,
  "errors": [
    
  ],
  "code": "Y001"
}
```

&nbsp;

### 유튜브 SearchAPI 는 일일 요청 제한량이 있지 않나?

유튜브 Data API를 사용하기 위해서는 키를 발급받아야 하며, 이 키에는 일일 요청 제한량이 정해져 있다.

우리 서비스의 사용자 요청이 많아질 경우 일일 요청 제한량을 초과할 수 있기 때문에 서비스의 장애가 올 수 있다.

이를  대비하기 위해 미리 여러개의 키를 발급받아놓은 후, 각 키의 제한량을 초과할 때 자동으로 다른 키를 이용하여 요청할 수 있도록 했다.

&nbsp;

### 키즈 채널은 어디서 가지고 오나?

http://cchart.xyz/ytcollect/YoutubeChannelTrackingServiceV2/kids/ 에서 키즈 채널 정보들을 가지고 온다.

채널 정보를 업데이트하기 위한 스케쥴링은 돌리지 않고 있다.

( 해당 서비스의 서버가 현재 동작하지 않는다. - 2020.05.05 )

