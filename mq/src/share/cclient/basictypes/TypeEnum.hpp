/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2000-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

/*
 * @(#)TypeEnum.hpp	1.4 06/26/07
 */ 

#ifndef TYPEENUM_HPP
#define TYPEENUM_HPP

#include <prtypes.h>

/** An enumeration of the types that appear in a serialized Java
 *  hashtable.  Do not change the order of the enum because various
 *  lookup tables depend on them.  */
enum TypeEnum { 
  BOOLEAN_TYPE,          // = 0,

  BYTE_TYPE,             // = 1,
  SHORT_TYPE,            // = 2,
  INTEGER_TYPE,          // = 3,
  LONG_TYPE,             // = 4,

  FLOAT_TYPE,            // = 5
  DOUBLE_TYPE,           // = 6,

  UTF8_STRING_TYPE,      // = 7,
  UTF8_LONG_STRING_TYPE, // = 8,

  HASHTABLE_TYPE,        // = 9,

  NUMBER_TYPE,           // = 10,
  UNKNOWN_TYPE,          // = 11,
  NULL_TYPE              // = 12
};

#endif // TYPEENUM_HPP
