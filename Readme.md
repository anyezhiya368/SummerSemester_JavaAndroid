### 2021夏季学期程序设计训练大作业——Java安卓新闻APP

* 本项目使用清华大学计算机系知识工程实验室提供的接口文档，具体使用方法详见接口文档

* apk文件文件位于/app/release路径下，可以下载至安卓手机中安装相应应用

* 更多详情参见报告文档

###2021夏季Java小学期大作业实验报告

王广晗 2020010947

#### 1、代码结构

本项目代码共分为两大部分：第一大部分为Java源码，Java源码负责处理程序逻辑地实现；第二大部分为使用android标记语言编写的标记文件，标记语言负责处理相应的静态分布及配置

##### Java源码

Java源码全部编写在com.example.newsapplication包内，分为以下五个部分：实现新闻抽象建模的相应类（位于newsmodel包内）、实现acitivity的类（类名以Activity结尾）、实现将新闻数据显示在相应布局上的adapter类（类名以Adapter结尾）、实现fragment的碎片类（类名以Fragment结尾）以及本地数据库所需要的MyDataBaseHelper类

* newsmodel包

  按照api中的数据层次，newsmodel中共有两个类，一个是NewsSource类，另一个是NewsBean类

  * NewsSource类

    NewsSource类包含四个成员变量，分别是String类型的pagesize，int类型的total，由NewsBean对象构成的列表data以及String类型的currentpage。四个变量的数目、位置、名字都与api中的json严格对应以便将json中的内容读入代码

  * NewsSourcePlanB类

    NewsSourcePlanB类为为处理image为列表类型的特殊数据而采用的备用类，其中的data被定义为NewsBeanPlanB对象构成的列表，而在后续代码中那个会将NewsBeanPlanB转化为NewsBean类

  * NewsBean类

    NewsBean类在api中对应data中的一项，在抽象的意义上，一个NewsBean就是一条新闻，由NewsBean构成的列表经由adapter展示在相应的布局上。向内部看，NewsBean类中一共包含八个成员变量，分别是存储读入图片字符串的image，存储新闻时间的publishTime，存储读入视频字符串的video，存储读入新闻标题的title，存储读入新闻链接的url，存储读入新闻来源的publisher，存储读入新闻类别的category以及存储读入新闻内容的content。变量命名与json中完全一致，以便将json文件读入代码。另外，由于json中image字符串的特性，需要单独编写一个parser对字符串进行解析，NewsBean中的成员函数getImage可返回包含该条新闻所有图片（非gif）url的字符串数组。

  * NewsBeanPlanB类

    NewsBeanPlanB类用于处理image属性为列表而非字符串的新闻，为和后续代码衔接，我们得到NewsBeanPlanB对象之后会在NewsFragment中将其转化为NewsBean类

* Activity类

  * MainActivity

    该类负责编写主菜单界面，主要功能如下

    * 编写主菜单toolbar的相应功能，包括搜索框（searchview）以及侧边栏收藏记录和浏览历史的展示（drawerlayout）
    * 编写侧边栏drawerlayout的布局以及点击事件的响应
    * 初始化newsfragment的列表，将其与viewpager绑定，并利用adapter将viewpager与tablayout绑定
    * 编写增删新闻类别的发送与接收响应（与setTypeActivity之间的intent传输）

  * SetTypeActivity

    该类负责编写增删新闻类别界面，主要功能如下

    * 实现布局绑定
    * 点击确认按钮之后，实现类别改变到MainActivity之间的传输

  * SearchableActivity

    该类负责编写搜索结果展示界面，主要功能如下

    * 接受用户在搜索框中输入的字符串
    * 对字符串进行解析，并向接口申请json
    * 对得到的json文件通过adapter展示在布局上
    * 处理toolbar的回退点击事件以及侧边栏收藏记录和浏览历史展示的侧边栏展示

  * DetailActivity

    该类负责编写新闻详情展示界面，主要功能如下

    * 接受adapter传来的NewsBean各项信息，并按照相应布局进行展示
    * 处理收藏与取消收藏的前端和数据库逻辑

  * CollectionActivity

    该类负责编写收藏记录展示界面，主要功能如下

    * 从数据库中读取相应数据，并且通过adatper展示在布局之中
    * 响应新闻详情页的取消收藏信号，以此保证通过收藏历史进入新闻详情页面并且取消收藏操作的顺利进行
    * 编写toolbar回退按钮的逻辑

  * HistoryActivity

    该类负责编写浏览历史展示界面，主要功能如下

    * 从数据库中读取相应数据，并且通过adapter展示在布局中
    * 编写toolbar回退按钮的逻辑

* Adapter类

  * NewsItemAdapter

    该类负责编写将新闻数据展示在相应布局上的逻辑，并且保证新闻点击后可向详情展示活动传递相应信息，主要功能如下

    * 定义不同布局所对应的newsholer
    * 通过新闻信息决定该条新闻应以哪种形式展示
    * 将新闻内筒绑定在布局上
    * 处理新闻点击事件（将该条新闻加入历史记录数据库，进入该条新闻的详情界面，将标题变灰）

  * FragmentAdapter

    该类负责将不同类别的newsfragment绑定到viewpager之上，主要功能如下

    * 绑定新闻内容到viewpager之上，方便新闻的左右切换
    * 为tablayout的相应栏目赋予标题

* Fragment类

  * NewsItemFragment

    该类负责实现封装在碎片之中的新闻列表（应用于MainActivity以及SearchActivity之中），主要功能如下

    * 接受并解析新闻列表的限制信息
    * 链接api获取新闻信息并绑定到本地的newsbeanlist中（采用多线程）
    * 通过其中封装的第三方库xrecyclerview实现下拉刷新和上拉加载的相应逻辑

* MyDatabaseHelper

  * MyDataBaseHelper

    该类负责实现数据库的创建和升级（再一次使用中不必要用到），主要功能如下

    * 创建两张分别用于存储收藏历史和浏览记录的表
    * 响应数据库的升级

##### 标记文件

标记文件是用标记语言所写成的文件，具有一定的代码特性，本项目中比较重要的几个文件夹有anim, drawable, layout, menu, values和xml

* anim文件夹

  anim文件夹中存储activity创建和消失时的简单动画效果

  * bottom_in：存储从底部向上出现的效果
  * bottom_out：存储向下消失的效果
  * bottom_silent: 控制程序栈中其他活动的文件，如果没有的话可能出现黑屏错误

* drawable文件夹

  drawable文件夹中存储了项目中用到的图片和图标，主要来源有iconfont阿里巴巴矢量库以及android studio自带的矢量库

* layout文件夹

  layout文件夹中存储了编写的布局文件，主要内容如下

  * activity_main.xml：该布局文件编写了主菜单界面的基本布局
  * activity_set_type.xml：该布局文件编写了设置类别界面的基本布局
  * activity_searchable.xml：该布局文件编写了搜索结果展示界面的基本布局
  * activity_detail.xml：该布局文件编写了新闻详情界面的基本布局
  * activity_collection.xml：该布局文件编写了收藏记录展示界面的基本布局
  * activity_history.xml：该布局文件编写了浏览历史展示界面的基本布局
  * fragment_newsitem.xml：该布局文件编写了fragment中新闻展示的基本布局
  * nav_header.xml：该布局文件编写了侧边栏的header展示的布局
  * newsitem_bigimage.xml：该布局文件编写了单张大图模式的新闻展示
  * newsitem_noimage.xml：该布局文件编写了没有图片模式的新闻展示
  * newsitem_oneimage.xml：该布局文件编写了单张小图模式的新闻展示
  * newsitem_threeimages.xml：该布局文件编写了三张图片模式的新闻展示
  * newsitem_video.xml：该布局文件编写了有视频模式的新闻展示

* menu文件夹

  menu文件夹中存储了项目用到的菜单文件

  * nav_menu.xml：该布局文件编写了侧边栏navigation_layout中的菜单
  * toolbarmain.xml：该布局文件编写了主菜单toolbar中的菜单
  * toolbat_search_menu.xml：该布局文件编写了搜索界面中toolbar的菜单（应该是toolbar，当时打错了）

* values文件夹

  values文件夹中存储了项目用到的一些常量

  * colors.xml：存储了用到的颜色
  * strings.xml：存储了用到的字符串
  * themes.xml：存储了用到的主题

* xml文件夹

  xml文件夹中存储了一些配置文件

  * network_security_config.xml：配置网络
  * searchable.xml：配置了主菜单界面中的toolbar上的searchview的提示信息等

##### 第三方库引入以及权限的配置

这部分内容并不算在源码的范畴之内，但却是保证程序正常运行而必不可少的，第三方库的引入标记在build.gradle中，而权限的配置在AndroidManifest.xml中

#### 2、具体实现

在了解代码框架以及个部分代码功能的基础之上，如果我们宏观地去审视这个项目，我们会发现这个项目主要分为四个大的功能模块，分别是：搜索处理与展示，新闻类别的展示、添加与删除，新闻数据的获取与展示以及收藏及历史数据库，下面我们从功能结构的视角，一一分析这些功能都是如何实现的。这里笔者假设读者已经读过第一部分并对个代码模块的功能有了初步的了解。

* 搜索模块

  搜索模块主要处理两个大的功能，一个是搜索需要的获取，另一个是搜索结果的展示，而两者又分别包括界面设计和逻辑实现两个模块

  * 搜索需要的获取

    * 界面设计

      参照各新闻软件的设计思路，我们将搜索框放置在主菜单界面的toolbar之中，点击放大镜图标自动弹出手机键盘并获得搜索界面，其中左侧的箭头按钮为撤销搜索，右侧的箭头按钮为进行搜索。

      代码实现方面，采用安卓组件serchview，将searchview添加到maintoolbar.xml的菜单文件之中并设置相应属性，即可获得效果。当确认搜索之后，MainActivity类会发出ACTION_SEARCH类型的Intent，并通过程序设计由SearchableActivity接受并解析

      **！亮点**：SearchView的合理使用使得搜索框的设计简洁优雅、美观大方

    * 逻辑实现

      由于笔者无力编写nlp算法，所以我设计了一套输入规范，即用户必须按照“关键词|分类|开始时间|结束时间”的方式输入，其中时间应按照api标准格式2021-09-02类型输入，如果某一项为空则不填，但是必须添加‘|’分隔符，如不按照此规范输入程序会弹出错误提示。

  * 搜索结果的展示

    * 界面设计

      SearchableActivity直接调用NewsItemFragment并利用其中的XRecyclerView（使用的带有下拉刷新和上拉加载功能的recyclerview第三方库）进行新闻列表的展示

    * 逻辑实现

      SearchableActivity直接调用NewsItemFragment并传入从Intent中获取的用户输入信息，利用其中的接口进行初始化、刷新、加载等操作

* 新闻类别模块

  新闻类别模块实现的功能大体分为两部分：新闻类别的初始化展示以及新闻类别的增加与减少

  * 新闻类别的初始化展示

    * 界面设计

      利用design库之中的tablayout控件，设置相应的字体、颜色和显示条的宽度、颜色，设置scrollable属性为true

    * 逻辑实现

      这里的核心是利用MainActivity之中的viewpager，我们首先将链接api的线程SearchThread，展示界面的xrecyclerview以及利用NewsItemAdapter进行的展示数据封装在NewsItemFragment类之中，再利用FragmentAdapter将NewsItemFragment构成的数组与viewpager绑定。每一个NewsItemFragment在构造的时候都有一个title成员变量，这个成员变量既可以用于绑定tablayout上的标签，也可以用于通过SearchThread进行初始化、刷新和加载工作

  * 新闻类别的增加和减少

    * 界面设计

      在主界面中，在tablayout的右侧我加入了一个按钮，点击它可以进入SetTypeActivity界面，SetTypeActivity界面之中有十个checkbox，如果现在这一类别在被选中的范围之中的话，该类别前面的checkbox会被选中，我们通过操作checbox勾选出我们想要留下的类别，并点击确认按钮，主菜单界面会自动改变其新闻类别，如果点击界面左上方的×，则会直接回退回主菜单界面

      **！亮点**：在tablayout的右侧加入一个按钮而非在actionbar的setting中进行操作，简洁美观

    * 逻辑实现

      主菜单界面和SetTypeActivity界面利用Intent进行来回通信，每次MainActivity启动通向SetTypeActivity的Intent的时候会传递当前的所有新闻类别（10个布尔类型），SetTypeActivity收到之后会进行解析并据此设置10个Checkbox的状态，当检测到确认按钮被点击之后会启动通向MainActivity的Intent（也包含10个布尔类型），这时MainActivity中的NewsFragmentList会重新遍历是个类别并加入已有的类别，从而显示改变的结果。另外，在初始化的时候，MainActivity中的10个类别都是存在的。

* 新闻数据获取与展示模块

  新闻数据的获取与展示包括四大部分功能，其一是通过api获取数据，其二是数据的刷新和加载，其三是将数据绑定在布局上，其四是详情页的打开

  * 数据的获取

    * 逻辑实现

      我们通过新建一个线程SearchThread类来进行数据的获取，我们通过已经获得的约束信息得到申请的url，利用OkHttp库获得json字符串，再利用Gson库将字符串转化为我们在newsmodel包中抽象出来的模型类，进而获得网络数据

  * 数据的刷新和加载

    * 界面设计

      数据的刷新和加载的界面设计由第三方库XRecyclerView提供，需要注意的是为了保证动画效果可以正确地展示出来，应该在执行相应的动作之前加入handler，并通过postDelayed方法延迟一段使劲（本项目中是1000ms）。XRecyclerView为我们提供了丰富的动画类型，可以逐个尝试并选出自己喜欢的

    * 逻辑实现

      * 刷新的逻辑实现是重新请求，并将请求的时间改为2021-09-02和2021-09-03，这样由于初始化时地时间为1949-10-10和2021-09-02，我们通过改变申请时间事实上完成了刷新操作，需要注意的是刷新结束之后该fragment的起止时间会永久设置为2021-09-02和2021-09-03，这样可以防止刷新之后再进行加载的时候之前的新闻发生突变

      * 加载的逻辑实现是重新请求，我们利用一个cursize变量维护当前的新闻总数目，初始化时申请15条新闻（这样的话起始的时候cursize为30），之后每次加载cursize就会增加15，这样达成不断加载的目的

        **！亮点**：在设置刷新和加载的逻辑结构时，我加入了判断设备是否联网的代码，这样的话如果设别已经没有联网，便会弹出一个Toast显示“You are not connected to the Internet"而不会出现crash的情况

  * 数据与布局的绑定

    * 逻辑实现

      数据与布局的绑定依照adapter来实现，在SearchThread获得json中的信息之后，程序会立即将这些信息转化成NewsBean对象的List，并将NewsBeanList传给NewsItemAdapter，在实现NewsItemAdapter的时候，我们定义了五种新闻表示形式对应的五种NewsHolder并 重写了四个函数。五种NewsHolder分别是NoImageHolder, OneImageHolder, ThreeImagesHolder, BigImageHolder和VideoImageHolder，四种函数分别是onCreateHolder, onBindHolder, getItemViewType和getCount

      * NoImageHolder：对应没有图片的情形，包括一个标题textView，一个来源textView

      * OneImageHolder：对应只有一张图片的情形，包括一个标题textView，一个来源textView，一个图片的imageView(这里使用的是第三方库roundediamgeview，因为这样的话可以使图片获得一定程度上的弧形四角，以后皆是如此)

      * ThreeImgaesHolder：对应三张图片的情形，包括一个标题textView，一个来源textView，三个图片的imageView

      * BigImageHolder：对应一张大图的情形，成分同OneImageHolder

      * VideoHolder：对应有视频的情形，包括一个标题textView，一个来源textView，一个videoView

      * onCreateHolder

        该函数通过viewtype参数找到对应的布局文件并返回相对应的newsHolder，具体对应关系如下：NoImageHolder对应0，OneImageHolder对应1，ThreeImagesHolder对应3，BigImageHolder对应100，videoHolder对应-1

      * onBindHolder

        该函数将newsbeanlist中包含的newsbean中的信息绑定到newsHolder上，并且设置点击事件，点击事件的响应包括两点，其一是进入详情界面，其二是将新闻信息加入历史数据库

        **！难点**：完成浏览过的标题变灰，这里通过两层机制，第一是点击过的标题变黑，其二，每一个newsbean被绑定到newsholder上时都会现在数据库中筛查，看这个newsbean是否曾经被浏览过，如果是的话，就在显示的时候把标题变成灰色

      * getItemViewType

        该函数输入一个newsbean在newsbeanlist中的位置，我们通过它的特性判断他的展示类型，如果没有图片返回0，其余如果如片数量大于等于3返回3，其余如果有视频返回-1，其余如果标题长度模二为零返回100，如果标题长度模二为1返回1。

      * getCount

        该函数返回adapter中新闻的条目数

  * 新闻详情页的展示

    * 界面设计

      界面通过activity_detail.xml实现，最上方是一个视频或者贴图，如果有视频的话就放置视频，如果没有视频有图片的话就放置第一张图片，如果既没有视频也没有图片的话就什么都不放，下面是标题和来源，在下面是正文，布局文件经过精心设计以保证美观。在右下角的位置有一个floatingactionbar，用来表示收藏与否，未收藏时为一个空心的五角星，收藏之后为一个实心的五角星

    * 逻辑实现

      其一，我们会按照上面讲到的逻辑实现视频和图片的摆放，其二，需要实现收藏按钮相关的逻辑

      **！难点**：收藏逻辑的编写，我们在传入intent的时候会事先从数据库中读取相应数据并判定该文章否已经收藏，传入之后据此进行展示，并且通过两个图表的相互切换实现收藏与取消收藏的转化，并且最终返回的时候将正确的数据情况留在数据库中

* 数据库模块

  数据库部分主要包括以下几点：数据库的初始化，数据库中数据的添加，数据库中数据的展示，数据库中数据的查询

  * 数据库的初始化

    * 逻辑实现

      数据库的初始化在MainActivity的onCreate函数当中实现，我在一个数据库中创建了两张表，分别名为”Collection"和“History”，这两张表中都有着相同的列，分别为id, title, url, image, video, publisher, time和content，两张表代表收藏和历史。在onCreate函数中我们创建的数据库都是version1，这是因为程序应该保存从下载以来的所有数据，而不是从本次启动开始的所有数据

  * 数据库中数据的添加

    * 逻辑实现

      在Collection数据库中，每一次点击空心星星图标，就会把数据加入数据库，每一次点击实心星星图标，就会把数据删除

      **！难点**：如果从收藏历史进入某一篇文章并且取消收藏，从该篇文章退出的时候收藏历史界面也应该发生相应的改变。这一点通过NewsItemAdapter中的startActivityForResult，DetailActivity中的setResult和CollectionActivity中的onActivityResult的联合设计实现

      在History数据库中，每一次点击新闻详情，都会把数据添加入数据库中

  * 数据库中数据的展示

    * 逻辑实现

      这一点主要有CollectionActivity和HistoryActivity来完成，这两个类中都有一个XRecyclerView，这两个XRecyclerView都会通过NewsItemAdapter中的接口实现数据的展示（这两个XRecyclerView都将刷新和加载的功能diaable了）

  * 数据库中数据的查询

    * 逻辑实现

      这一点借助于cursor，我们按照sqlite的使用规范，利用cursor查询相应表中相应行，相应列的数据，并进行相应操作

#### 3、总结与心得

项目总结：个人感觉从一个Java小白利用很短的时间写出一个尚可的新闻APP还是有一定的收获感的，我个人感觉这个项目在前端上的比重很大，很多的细节是需要自学的，找到靠谱的博客、视频和轮子对于项目的推进十分重要。我的工作的主体框架参考了b站up主你也喜欢吃饼干吗的视频，在此特别鸣谢。

个人收获：我认为相比于具体的java安卓开发的知识，我更加重视的收获是自学的能力以及对于完全未知的，接近实战的，大码量工作的信心。简而言之，我收获了查找资料并运用的能力，提振了自信，提升了组织代码的能力，可以说收获颇丰。

建议与意见：本项目主要部分是集中在前端开发，在编写代码的过程之中，客观地讲，我认为有很大的工作量是花费在寻找轮子，搞清楚接口之上的，而真正进行自己的思考和创造的功夫反而花的很少，或许这也是前端开发的特点，但毕竟对于初学者来讲，如果没有指导的话体验并不是很好。另外，助教的QA文档为什么回的频率不太对啊，还是觉得我们问的问题不方便回答啊orz


