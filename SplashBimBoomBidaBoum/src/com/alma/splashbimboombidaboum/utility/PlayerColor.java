package com.alma.splashbimboombidaboum.utility;

public enum PlayerColor {
	BLUE("#1278d3"), RED("#d32912"), PURPLE("#a112d3"), YELLOW("#ecf61f"), PINK("#ff03ec"), GREEN("#5cf50a");

	private String value;

	private PlayerColor(String value) {
		this.value = value;
	}

	public String getPlayerColor() {
		return this.value;
	}
}
