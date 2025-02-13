package com.corrector.dataproviders;

import java.util.function.Function;

public interface DataProvider<R> extends Function<String, R> {}
