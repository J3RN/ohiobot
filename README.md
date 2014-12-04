# ohiobot

An IRC bot that shouts "IO!" when you say "OH!"

## Usage

First, you'll need a `settings.json` file. I'll post an example soon, but I can
tell you that you'll need these fields:

- `"db-name"` - the name of your database. Mine is "ohiobot".
- `"db-user"` - the user with access to the database. Totally up to you.
- `"db-pass"` - the password for the user to access the database.
- `"irc-server"` - the URL of the IRC server (e.g. "irc.freenode.net")
- `"irc-port"` - the port of the IRC server (e.g. 6667)
- `"irc-nick"` - the nick of your bot on IRC (e.g. "Ohiobot")
- `"channels"` - an array of channels for the bot to join (e.g. ['#osuosc', '#cwdg'])

## License

Copyright © 2014 Jonathan Arnett

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
