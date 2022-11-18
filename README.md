# YoungJin

<!-- <details>
<summary>Week2</summary> -->

# Week2 - Adapter 패턴을 활용하는 UI 컴포넌트들
<img src="https://user-images.githubusercontent.com/48701368/195735354-4bf4ea43-1772-404b-827e-e5200ab1a595.gif" width="360" />

<br>

# Fragment Lifecycle callbacks
<img width="400" src="https://developer.android.com/static/images/guide/fragments/fragment-view-lifecycle.png">

- Activity Lifecycle 비교하면 생성 시에는 onViewCreated() - onViewStateRestored()가 추가로 있고, 파괴 시에는 onSaveInstanceState() - onDestroyView()가 추가되었다.
- Fragment Lifecycle보다 View Lifecycle이 더 짧다.

## Lifecycle.State enum
`INITIALIZED`, `CREATED`, `STARTED`, `RESUMED`, `DESTROYED`

## onAttach()
- 프래그먼트가 액티비티에 붙을 때 호출
- 인자로 Context가 주어짐

## onCreate()
- FragmentManager 에 add 됐을 때 도달하며 onCreate() 콜백함수를 호출
- 프래그먼트가 액티비티의 호출을 받아 생성
- onCreate() 이전에 onAttach()가 먼저 호출됨
- Bundle 타입으로 액티비티로부터 데이터(savedInstanceState)가 넘어옴. 이는 onSaveInstanceState()에 의에 저장된 Bundle 값임
- 여기서 또 알아야할 부분은 savedInstanceState 파라미터는 프래그먼트가 처음 생성 됐을 때만 null 로 넘어오며, onSaveInstanceState()를 재정의하지 않았더라도 그 이후 재생성부터는 non-null값으로 넘어옴
- Fragment View가 생성되지 않았기 때문에 Fragment의 View와 관련된 작업을 두기에 적절하지 않음 -> UI 초기화 불가능

## onCreateView()
- 레이아웃 inflate 담당 
- onCreateView()의 반환값으로 정상적인 Fragment View객체를 제공했을 때만 Fragment View의 Lifecycle이 생성됨
- savedInstanceState로 이전 상태에 대한 데이터 제공
- View와 관련된 객체를 초기화 할 수 있음

## onViewCreated()
- onCreateView()를 통해 반환된 View 객체는 onViewCreated()의 파라미터로 전달됨
- 이 시점부터는 Fragment View의 Lifecycle이 INITIALIZED 상태로 업데이트 됐음
- 때문에 View의 초기값 설정, LiveData 옵저빙, RecyclerView, ViewPager2에 사용될 Adapter 세팅 적절

## onViewStateRestored()
- 함수는 저장해둔 모든 state 값이 Fragment의 View 계층구조에 복원 됐을 때 호출됨
- 따라서 여기서부터는 체크박스 위젯이 현재 체크 되어있는지 등 각 뷰의 상태값을 체크할 수 있음

## onStart()
- Fragment가 사용자에게 보여질 수 있을 때 호출됨
- 주로 Fragment가 attach 되어있는 Activity의 onStart() 시점과 유사
- 이 시점부터는 Fragment의 child FragmentManager 통해 FragmentTransaction을 안전하게 수행 가능
- Fragment의 Lifecycle이 STARTED로 이동한 후에 Fragment View의 Lifecycle 또한 STARTED로 변환

## onResume()
- 사용자와 프래그먼트가 상호작용 할 수 있는 상태일 때 호출. 다시 말해 onResume()이 호출되지 않은 시점에서는 입력을 시도하거나 포커스를 설정하는 등의 작업을 임의로 하면 안된다는 것을 의미
- Fragment가 보이는 상태에서 모든 Animator와 Transition 효과가 종료되고, 프래그먼트와 사용자가 상호작용 할 수 있을 때 onResume() 호출됨
- onStart()와 마찬가지로 주로 Activity의 onResume()시점과 유사

## onPause()
- 사용자가 Fragment 를 떠나기 시작했지만 Fragment가 visible 일 때 onPause()가 호출
- 이 때 Faragment와 View의 Lifecycle이 PAUSED가 아닌 STARTED가 됨 // Lifecycle에 PAUSE와 STOP에 해당하는 상태가 없음

## onStop()
- Fragment가 더 이상 화면에 보여지지 않게 되면 onStop() 콜백 호출
- 부모 액티비티, 프래그먼트가 중단될 때, 상태가 저장될 때 호출
- View와 Lifecycle: STARTED → CREATED
- API 28버전을 기점으로 onSaveInstanceState() 함수와 onStop() 함수 호출 순서가 달라짐, 따라서 onStop()이 FragmentTransaction을 안전하게 수행하는 마지막 지점이 됨
<img width="400" src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbC4Zkm%2Fbtq9DwbxrgQ%2FIl287fhextuJbiCRZtZde1%2Fimg.png" />

## onDestoryView()
- 모든 exit animation, transaction이 완료되고 Fragment가 화면으로부터 벗어났을 경우 호출
- view와 lifecycle: CREATED → DESTROYED
- getViewLifecycleOwnerLiveData()의 리턴값으로 null 이 반환
- 가비지 컬렉터에 의해 수거될 수 있도록 Fragment View에 대한 모든 참조가 제거되어야 함

## onDestroy()
- Fragment가 제거되거나, FragmentManager가 destroy 됐을 경우, 프래그먼트의 Lifecycle 은 DESTROYED 상태가 되고, onDestroy() 호출
- Fragment Lifecycle의 끝을 알림
- onAttach() 가 onCreate()이전에 호출 됐던 것처럼 onDetach()또한 onDestroy()이후 호출됨

## onDetach()
- 프래그먼트가 액티비티로부터 해제되어질 때 호출됨

<br>

<details>
<summary>week1</summary>

# Week1 - View, ViewGroup과 UI 이벤트 처리하기
<img src="https://user-images.githubusercontent.com/48701368/193726884-fdbec67c-75ea-438d-b77c-008cd4e22b62.gif" width="360" />

<br>

# Activity Lifecycle callbacks
<img width="400" src="https://developer.android.com/guide/components/images/activity_lifecycle.png">

Activity 클래스는 Activity 상태가 변경되었음을 알 수 있는 여러 콜백을 제공한다. Activity 클래스는 수명 주기의 단계 간 전환을 탐색하기 위해 6가지 콜백 onCreate(), onStart(), onResume(), onPause(), onStop()및 onDestroy()를 제공한다. 시스템은 Activity이 새로운 상태에 들어갈 때 각 콜백을 호출한다.

<br>

## onCreate()
- 시스템이 처음 Activity를 생성할 때 실행되는 콜백이다.

- Activity 생성 시 Activity는 Created 상태가 된다.

- onCreate()에서는 Activity의 전체 수명 주기 동안 한 번만 발생해야 하는 기본 애플리케이션 로직을 수행한다. 예시는 아래와 같다.
  - Databinding 수행
  - Activity를 ViewModel과 연결
  - 일부 클래스 범위 변수를 인스턴스화

- 이 메소드는 Activity의 이전 상태를 포함하는 Bundle 객체인 매개 변수  InstanceState를 수신한다. 이전에 Activity가 존재하지 않은 경우 Bundle 객체 값은 null이다.

- onCreate() 메서드가 실행을 마치면 작업이 Started 상태로 전환되고, 시스템은 onStart() 메서드와 onResume() 메서드를 빠른 연속으로 호출한다.

```kotlin
lateinit var textView: TextView
var gameState: String? = null

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // 이전 상태값 복원
    gameState = savedInstanceState?.getString(GAME_STATE_KEY)

    // 화면 Layout 정의 및 뷰 생성
    setContentView(R.layout.main_activity)

    // 텍스트 뷰 초기화
    textView = findViewById(R.id.text_view)
}

override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
    textView.text = savedInstanceState?.getString(TEXT_VIEW_KEY)
}

override fun onSaveInstanceState(outState: Bundle?) {
    outState?.run {
        putString(GAME_STATE_KEY, gameState)
        putString(TEXT_VIEW_KEY, textView.text.toString())
    }
    super.onSaveInstanceState(outState)
}
``` 

## onStart()
- Activity가 Started 상태가 되면 시스템이 이 onStart()를 호출한다.

- 앱 이 포그라운드로 진입하여 상호작용이 되도록 준비하여 Activity가 사용자에게 보이도록 한다.

- 이 메서드는 앱이 UI를 유지하는 코드를 초기화하는 곳이다.

- onStart()는 매우 빠르게 완료되며 Created 상태와 마찬가지로 Activity가 Started 상태로 유지되지 않는다.

- 이 콜백이 완료되면 Activity가 Resumed 상태가 되고 시스템은 onResume()를 호출한다.

## onResume()
- Activity가 Resumed 상태로 전환되면 포그라운드로 이동한 다음 시스템이 onResume() 콜백을 호출한다.

- 앱이 사용자와 상호 작용하는 상태이다.

- 어떤 이벤트가 발생하여 앱에서 포커스가 떠날 때까지는 앱이 이 상태에서 머무르게 된다.

  - 예를 들어, 전화를 받거나 사용자가 다른 Activity를 탐색하거나 디바이스 화면이 꺼져 있는 경우이다.

- 중단 이벤트가 발생하면 작업이 Paused 상태로 전환되고 시스템이 onPause() 콜백을 호출한다.

- Paused 상태에서 Resumed 상태로 돌아가면 시스템이 다시 한번 onResume()을 호출한다.

- onResume()에서 리소스를 초기화하도록 구현했으면 onPause() 중에 리소스를 해제한다.
  - `ON_START` <-> `ON_STOP` : ON_START 이벤트 이후에 초기화를 했다면 ON_STOP 이벤트 이후에 해제하거나 종료한다.
  - `ON_RESUME` <-> `ON_PAUSE` : ON_RESUME 이벤트 이후에 초기화하는 경우 ON_PAUSE 이벤트 이후에 해제한다.

```kotlin
// 수명주기를 알고 있는 컴포넌트가 ON_RESUME 이벤트를 수신할 때 카메라에 액세스하는 예시
class CameraComponent : LifecycleObserver {

    ...

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun initializeCamera() {
        if (camera == null) {
            getCamera()
        }
    }

    ...
}
```

## onPause()
- Activity가 더 이상 포그라운드에 있지 않음을 나타낸다.(팝업이 띄어지거나 포커스를 잃어버리게 될 경우. 단, 멀티 윈도우 모드에 있는 경우 계속 표시는 될 수 있음).

- Activity가 Paused 상태로 전환되는 데는 몇 가지 이유가 있다.
  - 가장 흔한 케이스는 일부 이벤트로 앱 실행이 중단되는 것이다.
  - Android 7.0(API 레벨 24) 이상에서는 여러 앱이 멀티 윈도우 모드로 실행됩니다. 항상 하나의 앱(윈도우)에만 포커스가 있기 때문에 시스템은 다른 모든 앱을 일시 중지한다.
  - 다이얼로그 같은 새로운 반투명 Activity가 열릴 때이다. Activity가 여전히 부분적으로 보이지만 초점이 맞지 않는 한 Paused 상태로 남아 있다.
- onPause()에서 시스템 리소스, 센서(예: GPS)에 대한 핸들 또는 작업이 일시 중지되고 사용자에게 필요하지 않은 동안 배터리 수명에 영향을 줄 수 있는 리소스를 해제할 수 있다.

- 멀티 윈도우 모드인 경우 Paused 상태인 Activity가 여전히 나타날 수 있다. 따라서 UI 관련 리소스를 완전히 해제하거나 조정하는 것을 멀티 윈도우 모드를 더 잘 지원하려면 onPause() 대신 onStop()를 사용하는 것을 고려해야한다.

- onPause()실행은 매우 짧으며 저장 작업을 수행하기에 충분한 시간이 필요하지 않다. 따라서 응용 프로그램 또는 사용자 데이터를 저장하거나 네트워크 호출을 하거나 데이터베이스 트랜잭션을 실행할 때 onPause()를 사용하면 안 된다. OnStop()중에 과부하 종료 작업을 수행해야 한다.

- onPause() 메서드가 완료되었다고 작업이 일시 중지된 상태가 되는 것은 아니다. 대신 Activity가 다시 시작되거나 사용자가 완전히 볼 수 없게 될 때까지 Activity는 이 상태로 유지된다.

```kotlin
// ON_RESUME 이벤트가 수신된 후 초기화된 카메라를 해제한다.
class CameraComponent : LifecycleObserver {
    ...
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun releaseCamera() {
        camera?.release()
        camera = null
    }
    ...
}
```

## onStop()
- Activity가 사용자에게 완전히 보이지 않게 되면 Stopped 상태에 들어가고, 시스템은 onStop() 콜백을 호출한다.

- 예를 들어 새로 시작된 Activity가 화면 전체를 차지할 경우에 적용된다.

- 시스템은 Activity의 실행이 완료되어 종료될 시점에 onStop()을 호출할 수도 있다.

- onStop()에서는 앱이 사용자에게 보이지 않는 동안 앱은 필요하지 않은 리소스를 해제하거나 조정해야한다.

- 예를 들어 앱은 애니메이션을 일시중지하거나, 세밀한 위치 업데이트에서 대략적인 위치 업데이트로 전환할 수 있다.

- onPause() 대신 onStop()을 사용하면 사용자가 멀티 윈도우 모드에서 활동을 보고 있더라도 UI 관련 작업이 계속 진행된다.

- 또한 onStop()을 사용하여 CPU를 비교적 많이 소모하는 종료 작업을 실행해야한다.

- 예를 들어 정보를 DB에 저장할 적절한 시기를 찾지 못했다면 onStop() 상태일 때 저장할 수 있다.

- Activitiy가 Stopped 상태에 들어가면 Activity 객체는 메모리 안에 머무르게 된다. 이 객체가 모든 상태 및 멤버 정보를 관리하지만 window 매니저와 연결되어 있지는 않다.

- Activity가 다시 시작되면 이 정보를 다시 호출한다. 최상위 상태가 Resumed 상태인 콜백 메서드 중에 생성된 구성요소는 다시 초기화할 필요가 없다.

- 또한 시스템은 레이아웃에 있는 각 View 객체의 현재 상태도 기록한다. 따라서 사용자가 EditText 위젯에 텍스트를 입력하면 해당 내용이 저장되기 때문에 이를 저장 및 복원할 필요가 없다.

- Activity는 Stopped 상태에서 다시 시작되어 사용자와 상호작용하거나, 실행을 종료하고 사라진다.

- 활동이 다시 시작되면 시스템은 onRestart()를 호출한다.

```kotlin
// 초안 내용을 로컬 DB에 저장하는 onStop()을 구현한 예제
override fun onStop() {
    super.onStop()

    val values = ContentValues().apply {
        put(NotePad.Notes.COLUMN_NAME_NOTE, getCurrentNoteText())
        put(NotePad.Notes.COLUMN_NAME_TITLE, getCurrentNoteTitle())
    }

    asyncQueryHandler.startUpdate(
            token,
            null,     
            uri,    
            values,   
            null,
            null     
    )
}
```

## onDestroy()
- Activity가 실행을 종료하면 시스템은 onDestroy()를 호출한다.

- 시스템은 다음과 같은 이유로 이 콜백을 호출한다.
  - Activitiy 종료
  - configuration change(예: 장치 회전 또는 멀티 윈도우 모드)으로 인해 시스템이 일시적으로 Activitiy를 중단

- Activity가 configuration chnage으로 인해 다시 생성될 경우 ViewModel은 그대로 보존되어 다음 Activity 인스턴스에 전달되므로 추가 작업이 필요하지 않다. Activity가 다시 생성되지 않을 경우 ViewModel은 onCleared()를 호출하여 Activity가 소멸되기 전에 모든 데이터를 정리해야 한다.

- onDestroy()는 Activity가 수신하는 마지막 수명 주기 콜백이 된다.

- onDestroy() 콜백은 이전의 콜백(예: onStop())에서 아직 해제되지 않은 모든 리소스를 해제해야 한다.

## Summary
|Method|Activity State|Description|
|---|---|---|
|**onCreate()**|만들어짐|Activity 생성할 때|
|**onStart()**|화면에 나타남|화면에 보여지기 시작할 때|
|**onResume()**|현재 실행 중 화면에 나타남|화면에 나타나 있고 실행중일 때|
|**onPause()**|화면이 가려짐|Activity화면의 일부가 다른 Activity에 가려짐|
|**onStop()**|화면이 없어짐|다른 Activity의 실행으로 완전히 가려짐|
|**onDestroy()**|종료됨|Activity 종료됨|

## Reference
[Activity 생명주기 표 출처](https://bbaktaeho-95.tistory.com/62)
[Activity Lifecycle 공식문서](https://developer.android.com/guide/components/activities/activity-lifecycle)

<br>

</details>

<br>

<details>
<summary>Rules</summary>

## This is your repository for 31st SOPT Android Development Seminar and Assignment

### 레포지터리에는 총 3개의 브랜치가 있습니다.

- master
  - 가장 기본이 되는 브랜치입니다. 새로운 연습 환경을 만들고자 할때 이 브랜치에서 새로운 브랜치를 파셔서 만드시면 됩니다.
- develop/view
  - 여러분의 대부분의 과제는 이 브랜치에서 이뤄지게 될 것입니다.
  - develop/view 브랜치에서 새로운 feature 브랜치를 파고 작업을 진행하면서
  - 과제를 완료하면 해당 과제를 develop/view에 PR을 올려주시고 코드리뷰를 받으시면 됩니다.
  - 머지까지 완료하시면 과제 완료로 인정하겠습니다.
- develop/compose
  - 이번 기수 심화과제인 Compose 과제를 진행하는 브랜치입니다.
  - develop/compose 브랜치에서 심화과제에 해당하는 내용의 작업들을 진행해주시면 됩니다.
  - 기존 view 브랜치들과는 연관관계가 없어야 합니다.
  - 과제를 완료하면 해당 과제를 develop/compose에 PR을 올려주시고 코드리뷰를 받으시면 됩니다.
  - Compose 개발환경은
    - Kotlin: 1.7.0
    - Compose: 1.2.1
  - 으로 맞춰놓겠습니다.
</details>