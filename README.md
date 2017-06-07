# jwt-tool
[![Travis](https://img.shields.io/travis/GreyTeardrop/jwt-tool.svg)](https://travis-ci.org/GreyTeardrop/jwt-tool)

Simple command-line tool to manipulate JWT tokens

## Usage

### Interactive mode

Run `jwt-tool` without any parameters to start interactive console UI:

```bash
./jwt-tool
```

### Batch command line mode

Run `jwt-tool` with command line parameters to trigger batch mode

```
./jwt-tool <command> [<parameters>]
./jwt-tool --help
```

The only supported command for now is `create`. Parameters are:

- `--to-clipboard` to export generated JWT token to clipboard
- `--payload <name> <value>` to add key-value pair to JWT payload/claim. Required claims are `user_id` and `email`.
