package com.matuga.mcp.model;

public record Session(
    String day, String time, String title, String type, String[] speakers, String room) {}
