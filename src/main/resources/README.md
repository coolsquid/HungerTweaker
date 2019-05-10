# HungerTweaker
HungerTweaker exposes the [AppleCore](https://github.com/squeek502/AppleCore) API to [CraftTweaker](https://github.com/CraftTweaker/CraftTweaker) scripts, and can be used to modify a variety of food and hunger-related properties.

HungerTweaker consists of two parts. Firstly, it includes a simplified wrapper around the AppleCore API. This can be used to set a variety of default values, as well as modify the [properties of food items](https://github.com/coolsquid/HungerTweaker/wiki/FoodValues). Secondly, it provides access to most of AppleCore's [events](https://github.com/coolsquid/HungerTweaker/wiki/HungerEvents). This can be used to dynamically modify and react to changes in a player's hunger, exhaustion, starvation, and regen.

The simplified wrapper is designed to be easy to use, and largely consists of static ZenMethods such as `mods.hungertweaker.Hunger.setMaxHunger(20)`. It has access to no other context than the previous value of the property, which can be used in [expressions](https://github.com/coolsquid/HungerTweaker/wiki/Expression), such as `mods.hungertweaker.Hunger.setMaxHunger("x/2")`.

The event system functions largely like CraftTweaker's own event system. Scripts can register event handler functions, which are executed whenever the event occurs and have access to context, such as the IPlayer in question. As such, the event system can be used to produce far more advanced results than the simplified wrapper.

HungerTweaker attempts to apply its changes after all other mods. The simplified options are handled before the events, and event handlers may override the default values set by the simplified options.

For a more complete overview, visit the HungerTweaker wiki.
https://github.com/coolsquid/HungerTweaker/wiki/