# SplashBimBoomBidaBoum

### Auteurs : Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme

## Description :
Notre projet consiste à la création d'un jeu multijoueur. Le but de notre jeu est très simple, chaque joueur incarne un petit carré qui peut se déplacer dans tous les sens sur une scène 2D. Des obstacles apparaissent tout les x temps (avec x pouvant être modifié), si un joueur touche un obstacle, il meurt. Le dernier joueur en vie gagne.

## Réalisation :
Pour ce faire, nous avons intégralement développé notre jeu en java. Autant la partie client que serveur. Nous avons donc créé un unique projet java qui regroupe 2 packages distincts. L'un est un package pour le serveur et le second pour les clients. L'avantage de cela, est le fait que nous n'avons pas à recopier les interfaces de communication de chaque côté, car les client/serveur se trouvent dans le même projet. De plus, l'interface graphique des clients a été réalisée à l'aide de JavaFX.

## Scénario du déroulement :
Lorsque vous lancez un client, une première fenêtre apparaît. Dans celle-ci, vous pouvez y renseigner votre pseudo, entrer dans la prochaine fenêtre ou quitter le jeu. Si vous ouvrez la prochaine fenêtre, vous avez le choix de rentrer dans un lobby aléatoirement, entrer dans un lobby dont vous connaissez l'addresse ou retourner en arrière. Une fois rentré dans le lobby, la partie se lancera automatiquement lorsque tous les joueurs du lobby seront prêt (2 joueur minimum pour lancer, et 4 joueur maximum par lobby). Une fois la partie lancé, vous aurez un petit carré de couleur aléatoire qui vous sera assigné. Déplacez votre petit carré afin d'éviter les obstacles et finir par être le dernier en vie dans la partie. Une fois qu'il ne reste plus qu'un joueur en vie, vous êtes tous redirigé sur une fenêtre affichant le classement. Enfin vous pouvez appuyer sur retour pour revenir au lobby.

## Fonctions non implémentées :
La gestion des déconnexions des joueurs n'est pas implémentée. C'est-à-dire que si un joueur ferme sa fenêtre à l'aide de la croix ou en réalisant un alt+f4 ou même en perdant la connexion avec le serveur, le serveur va commencer par lever des exceptions et les joueurs restant aussi. 

## Lancement :
Pour lancer le jeu, il suffit de lancer le main du serveur avant de lancer le main d'autant de client que l'on veut.

## Organisation du projet :
* Le répertoire du serveur : [Serveur](SplashBimBoomBidaBoum/src/com/alma/splashbimboombidaboum/server)
* Le répertoir du client : [Client](SplashBimBoomBidaBoum/src/com/alma/splashbimboombidaboum/client)
* Le Main du serveur : [Serveur Main](SplashBimBoomBidaBoum/src/com/alma/splashbimboombidaboum/server/Main.java)
* Le Main du client : [Client Main](SplashBimBoomBidaBoum/src/com/alma/splashbimboombidaboum/client/Main.java)
