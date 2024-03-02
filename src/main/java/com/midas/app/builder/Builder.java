package com.midas.app.builder;

public interface Builder<Response, Request> {

  Response build(Request request);
}
