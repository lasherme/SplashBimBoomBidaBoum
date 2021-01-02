# SplashBimBoomBidaBoum

### Auteurs : Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme

## Description :
Notre projet consiste à la création d'un jeu multijoueur. Le but de notre jeu est très simple, chaque joueur incarne un petit carré qui peut se déplacer dans tous les sens sur une scène 2D. Des obstacles apparaissent tout les x temps (avec x pouvant être modifié), si un joueur touche un obstacle, il meurt. Le dernier joueur en vie gagne.

## Réalisation :
Pour ce faire, nous avons intégralement développé notre jeu en java. Autant la partie client que serveur. Nous avons donc créé un unique projet java qui regroupe 2 packages distincts. L'un est un package pour le serveur et le second pour les clients. L'avantage de cela, est le fait que nous n'avons pas à recopier les interfaces de communication de chaque côté, car les client/serveur se trouvent dans le même projet. De lpus, l'interface graphique des clients a été réalisée à l'aide de javaFX.
