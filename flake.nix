{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = {
    self,
    nixpkgs,
    flake-utils,
  }:
    flake-utils.lib.eachDefaultSystem (system: let
      pkgs = import nixpkgs {
        inherit system;
      };
    in {
      devShells.default = pkgs.mkShell {
        name = "frello";
        packages = with pkgs; [
          jdk
          maven
          postgresql_14
          nodejs-18_x
          yarn
        ];

        shellHook = ''
          . scripts/db/bootstrap.sh
        '';
      };
    });
}
