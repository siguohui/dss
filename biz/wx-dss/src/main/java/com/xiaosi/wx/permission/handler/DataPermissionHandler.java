package com.xiaosi.wx.permission.handler;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.PlainSelect;

public interface DataPermissionHandler {

    Expression getSqlSegment(PlainSelect plainSelect, String whereSegment);
    Expression getSqlSegment2(PlainSelect plainSelect, String whereSegment);

    Expression getSqlSegment(Expression where, String mappedStatementId);
}
