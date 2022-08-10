# TimeRestricter

## What can this simple plugin do for me?

- Kick players if they have no time left
- Configure at which time and at which day the plugin should refill the time

## Config

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

## Commands

- /tr-time ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ Shows the player the remaining time.
- /tr-time <player_name> ⠀ ⠀ ⠀ ⠀Lookup remaning time of other players.
- /tr-playerstime ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ View remaining time of all players.

- /tr-enabled ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ Show if plugin enabled or disabled.
- /tr-enabled <true/false> ⠀ ⠀ ⠀ ⠀ Enable or disable the plugin (no reload or restart needed).

- /availableMinutes ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ Show how much minutes are configured in config.
- /availableMinutes <minutes> ⠀ ⠀ ⠀Set the amount of minutes in config.

- /tr-fillupTime ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ Show when time gets filled up again.
- /tr-fillupTime 06:10 1234567 ⠀ ⠀Set time and days (Mon = 1, Tue = 2, etc) when the time fillup should occour.
  
- /tr-servertime  ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀Show current time from the server.
  
- /tr-timereset  ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀ ⠀Reset time for everybody.


## Permissions
  
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
