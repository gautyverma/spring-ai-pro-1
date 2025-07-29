package com.matuga.mcp.model;

import java.util.List;

public record Conference(
    String name, int year, String[] dates, String location, List<Session> sessions) {}
