# Composer Pager Indicator

[![](https://jitpack.io/v/SmartToolFactory/Compose-PagerIndicator.svg)](https://jitpack.io/#SmartToolFactory/Compose-PagerIndicator)

Indicators for **Horizontal** or **Vertical** pager with different orientation, color,
size options and optional touch feature.

https://user-images.githubusercontent.com/35650605/182871485-4f2551a8-4d57-41aa-b7b1-2e4d8c9dffdb.mp4



## Gradle Setup

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
        implementation 'com.github.SmartToolFactory:Compose-PagerIndicator:Tag'
}	
```

## ⚠️ This is alpha version, and under development

This version is pretty raw and very ungraceful logic to scale items based on their position.
In future versions will add
offsetting items will change of color, position from absolute position when pager offset change,
color animation change
and infinite item support.
