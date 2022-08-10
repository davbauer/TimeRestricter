# Spigot Plugin: TimeRestricter

## Officially supported Minecraft versions:

- 1.19

### Where can I download the plugin?

- Head over to the releases on the right side to download the latest version.

## What can this simple plugin do for me?

- Kick players if they have no time left
- Configure at which time and at which day the plugin should refill the time

## Config

```txt
./TimeRestricter/config.yaml
```

```yaml

enabled: true
opPlayersHaveFullPermissions: true
allowBasicCommandsWithoutPermissions: true
availableMinutes: 100
fillUpTimeHour: 4
fillUpTimeMinute: 15
fillUpTimeDays:
  - 1
  - 2
  - 3
  - 4
  - 5
  - 6
  - 7
  
 ```
 
 ```txt
 The numbers on the `fillUpTimeDays` property are days:
 (1 = Mon, 2 = Tue, 3 = Thu, 4 = Wed, 5 = Fri, 6 = Sat, 7 = Sun)
 
 The `fillUpTime*` properties describe when the time should be reset for every player.
 
 The `allowBasicCommandsWithoutPermissions` property gives every user basic access like:
 - /tr-time
 - /tr-time <player_name>
 - /tr-playerstime
 
 - /tr-enabled
 - /tr-available Minutes
 - /tr-fillupTime
 - /tr-servertime
 
With the `allowBasicCommandsWithoutPermissions` every player has the ability to 
view and check states, not change them.

If you want to completly control who can access which commands, turn the option 
off and use the listed permissions.
 ```
 

## Commands

```txt
- /tr-time                         Shows the player the remaining time.
- /tr-time <player_name>           Lookup remaning time of other players.
- /tr-playerstime                  View remaining time of all players.

- /tr-enabled                      Show if plugin enabled or disabled.
- /tr-enabled <true/false>         Enable or disable the plugin (no reload or restart needed).

- /tr-availableMinutes                Show how much minutes are configured in config.
- /tr-availableMinutes <minutes>      Set the amount of minutes in config.

- /tr-fillupTime                   Show when time gets filled up again.
- /tr-fillupTime 06:10 1234567     Set time and days (1 = Mon, 2 = Tue, etc) when the time fillup should occour.
  
- /tr-servertime                   Show current time from the server.
  
- /tr-timereset                    Reset time for everybody.
```

## Permissions

```txt
  
- timerestricter.view_time_self
- timerestricter.view_time_others
- timerestricter.view_time_all_player

- timerestricter.view_plugin_enabled
- timerestricter.change_plugin_enabled
  
- timerestricter.view_available_minutes
- timerestricter.change_available_minutes

- timerestricter.view_filluptime
- timerestricter.change_filluptime
  
- timerestricter.reset_time

```
