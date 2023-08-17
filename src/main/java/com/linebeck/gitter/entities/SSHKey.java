package com.linebeck.gitter.entities;

import java.io.File;

public record SSHKey(String name, File location, String password) {}
